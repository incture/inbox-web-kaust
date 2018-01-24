package com.incture.pmc.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.incture.pmc.workbox.dto.ResponseMessage;
import com.incture.pmc.workbox.dto.WorkboxRequestDto;



/**
 * Contains utility functions to be used for consuming oData Services
 * 
 * @version R1
 */
public class ODataServicesUtil {

	public static String USERNAME;
	public static String PASSWORD; 


	public static ODataEntry readEntry(Edm edm, String serviceUri, String contentType, String entitySetName, String keyValue)
			throws IOException, ODataException {
		return readEntry(edm, serviceUri, contentType, entitySetName, keyValue, null);
	}

	public static ODataEntry readEntry(Edm edm, String serviceUri, String contentType, String entitySetName, String keyValue,
			String expandRelationName) throws IOException, ODataException {
		EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
		String absolutUri = createUri(serviceUri, entitySetName, keyValue, expandRelationName);

		InputStream content = execute(absolutUri, contentType, PMCConstant.HTTP_METHOD_GET);

		return EntityProvider.readEntry(contentType, entityContainer.getEntitySet(entitySetName), content,
				EntityProviderReadProperties.init().build());
	}

	private static String createUri(String serviceUri, String entitySetName, String id) {
		return createUri(serviceUri, entitySetName, id, null);
	}

	private static String createUri(String serviceUri, String entitySetName, String id, String expand) {
		final StringBuilder absolutUri = new StringBuilder(serviceUri).append(PMCConstant.SEPARATOR)
				.append(entitySetName);
		if (id != null) {
			absolutUri.append("(").append(id).append(")");
		}
		if (expand != null) {
			absolutUri.append("/?$expand=").append(expand);
		}
		return absolutUri.toString();
	}

	public static InputStream execute(String relativeUri, String contentType, String httpMethod) throws IOException {
		InputStream content  = null;
		HttpURLConnection connection = null;
		try {
			connection = initializeConnection(relativeUri, contentType, httpMethod);
			connection.connect();
			checkStatus(connection);
			content = connection.getInputStream();
			content = logRawContent(httpMethod + " request on uri '" + relativeUri + "' with content:\n  ", content, "\n");

		}catch(Exception e){
			System.err.println("[PMC][ODataServicesUtil][execute][error]  "+ e.getMessage());
		}
		finally{
			if(!ServicesUtil.isEmpty(connection)){
				connection.disconnect();
			}
		}
		return content;
	}


	private static HttpURLConnection initializeConnection(String absolutUri, String contentType, String httpMethod)
			throws MalformedURLException, IOException {
		URL url = new URL(absolutUri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setRequestMethod(httpMethod);
		connection.setRequestProperty(PMCConstant.HTTP_HEADER_ACCEPT, contentType);
		//		String userpass = USERNAME + ":" + PASSWORD ;
		//		String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		connection.setRequestProperty("Authorization", getBasicAuth());

		if (PMCConstant.HTTP_METHOD_POST.equals(httpMethod) || PMCConstant.HTTP_METHOD_PUT.equals(httpMethod)) {
			connection.setDoOutput(true);
			connection.setRequestProperty(PMCConstant.HTTP_HEADER_CONTENT_TYPE, contentType);
		}

		return connection;
	}

	private static HttpStatusCodes checkStatus(HttpURLConnection connection) throws IOException {
		HttpStatusCodes httpStatusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
		if (400 <= httpStatusCode.getStatusCode() && httpStatusCode.getStatusCode() <= 599) {
			throw new RuntimeException("Http Connection failed with status " + httpStatusCode.getStatusCode() + " "
					+ httpStatusCode.toString());
		}
		return httpStatusCode;
	}

	private static InputStream logRawContent(String prefix, InputStream content, String postfix) throws IOException {
		if (PMCConstant.PRINT_RAW_CONTENT) {
			byte[] buffer = streamToArray(content);
			return new ByteArrayInputStream(buffer);
		}
		return content;
	}

	private static byte[] streamToArray(InputStream stream) throws IOException {
		byte[] result = new byte[0];
		byte[] tmp = new byte[8192];
		int readCount = stream.read(tmp);
		while (readCount >= 0) {
			byte[] innerTmp = new byte[result.length + readCount];
			System.arraycopy(result, 0, innerTmp, 0, result.length);
			System.arraycopy(tmp, 0, innerTmp, result.length, readCount);
			result = innerTmp;
			readCount = stream.read(tmp);
		}
		stream.close();
		return result;
	}

	public static Edm readEdm(String serviceUrl,String username,String password) throws IOException, ODataException {
		if(!ServicesUtil.isEmpty(username)&&!ServicesUtil.isEmpty(password)){
			USERNAME = username;
			PASSWORD = ServicesUtil.getDecryptedText(password);
		}
		InputStream content = execute(serviceUrl + PMCConstant.SEPARATOR + PMCConstant.METADATA,
				PMCConstant.APPLICATION_XML, PMCConstant.HTTP_METHOD_GET);
		return EntityProvider.readMetadata(content, false);
	}

	public static ODataFeed readFeed(Edm edm, String serviceUri, String contentType, String entitySetName)
			throws IOException, ODataException {
		EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
		String absolutUri = createUri(serviceUri, entitySetName, null);

		InputStream content = execute(absolutUri, contentType, PMCConstant.HTTP_METHOD_GET);
		return EntityProvider.readFeed(contentType, entityContainer.getEntitySet(entitySetName), content,
				EntityProviderReadProperties.init().build());
	}
	public static String readActions(String serviceUri, String contentType)
			throws IOException, ODataException {
		HttpURLConnection connection = null;
		String actions="";
		try{
			connection = initializeConnection(serviceUri, contentType, PMCConstant.HTTP_METHOD_GET);

			connection.connect();
			checkStatus(connection);

			InputStream content = connection.getInputStream();
			byte[] buffer = streamToArray(content);
			String st = new String(buffer);
			if(st.contains("Submit")){
				actions = actions +"Submit";
			}
			if(st.contains("Approve")){
				if(!ServicesUtil.isEmpty(actions)){
					actions = actions +",";	
				}
				actions = actions +"Approve";
			}
			if(st.contains("Reject")){
				if(!ServicesUtil.isEmpty(actions)){
					actions = actions +",";	
				}
				actions = actions +"Reject";
			}
		}catch(Exception e){
			System.err.println("[PMC][ODataServicesUtil][readActions][error]  "+ e.getMessage());
		}
		finally{
			if(!ServicesUtil.isEmpty(connection)){
				connection.disconnect();
			}
		}
		return actions;
	}




	public static InputStream getAttachment(String instanceId,String sapOrigin,String username ,String scode, String id){
		//	System.err.println("[PMC][ODataServicesUtil][Xpath][getAttachment]initiated ");
		if(!ServicesUtil.isEmpty(username) && !ServicesUtil.isEmpty(scode))
		{
			if(!ServicesUtil.isEmpty(sapOrigin) && !ServicesUtil.isEmpty(instanceId)&& !ServicesUtil.isEmpty(id)){
				USERNAME = username;
				PASSWORD = ServicesUtil.getDecryptedText(scode);
				String serviceUri = "http://sthcigwdq1.kaust.edu.sa:8005/sap/opu/odata/IWPGW/TASKPROCESSING;mo;v=2/AttachmentCollection(SAP__Origin='"+sapOrigin+"',InstanceID='"+instanceId+"',ID='"+id+"')/$value";

				//HttpURLConnection connection = null;
				InputStream inputStream = null;
				CloseableHttpClient httpclient = HttpClients.createDefault();
				try{
					HttpGet httpGet = new HttpGet(serviceUri);
					httpGet.setHeader("Authorization", getBasicAuth());
					CloseableHttpResponse response_get = httpclient.execute(httpGet); 
					HttpEntity entity_get = response_get.getEntity();  
					inputStream = entity_get.getContent();

				}catch(Exception e){
					System.err.println("[PMC][ODataServicesUtil][getAttachment][error]  "+ e.getMessage());
				}
				finally{
					/*if(!ServicesUtil.isEmpty(connection)){
						connection.disconnect();
					}*/
				}
				//	System.err.println("[PMC][ODataServicesUtil][Xpath][getAttachment]exited with  " +inputStream);
				return inputStream;
			}
			else{
				return null;
			}
		}
		else {
			return null;
		}
	}


	public static ResponseMessage executeAction(WorkboxRequestDto requestDto){

		ResponseMessage returnMessage = new ResponseMessage();
		if(requestDto.getTaskType().equals("AddComment")){
			returnMessage.setMessage(" Failed to add Comment");	
		}else{
			returnMessage.setMessage(" Failed to "+ requestDto.getTaskType());
		}
		returnMessage.setStatus("FAILURE");
		returnMessage.setStatusCode("1");
		//	System.err.println("[PMC][ODataServicesUtil][Xpath][actions]initiated ");
		if(!ServicesUtil.isEmpty(requestDto.getUserId()) && !ServicesUtil.isEmpty(requestDto.getScode()))
		{
			if(!ServicesUtil.isEmpty(requestDto.getSapOrigin()) && !ServicesUtil.isEmpty(requestDto.getInstanceId())&& !ServicesUtil.isEmpty(requestDto.getTaskType())){
				USERNAME = requestDto.getUserId();
				PASSWORD = ServicesUtil.getDecryptedText(requestDto.getScode());
				String serviceBase = "http://sthcigwdq1.kaust.edu.sa:8005/sap/opu/odata/IWPGW/TASKPROCESSING;mo;v=2/"+requestDto.getTaskType()+"?";
				String	serviceUri = "SAP__Origin='"+requestDto.getSapOrigin()+"'&InstanceID='"+requestDto.getInstanceId()+"'";
				try {
					if(!ServicesUtil.isEmpty(requestDto.getDecisionKey())){
						serviceUri=serviceUri+	"&DecisionKey='"+requestDto.getDecisionKey()+"'";
					}
					if(!ServicesUtil.isEmpty(requestDto.getForwardTo())){
						serviceUri=serviceUri+	"&ForwardTo='"+requestDto.getForwardTo()+"'";
					}
					if(!ServicesUtil.isEmpty(requestDto.getText())){
						serviceUri=serviceUri+	"&Text='"+ URLEncoder.encode(requestDto.getText() , "UTF-8")+"'";
					}

					if(!ServicesUtil.isEmpty(requestDto.getItemNo())){
						if(ServicesUtil.isEmpty(requestDto.getComments())){
							serviceUri=serviceUri+"&Comments='*AllItems*";
						}
						else{
							serviceUri=serviceUri+"&Comments='*"+ URLEncoder.encode(requestDto.getComments() , "UTF-8")+"*"; 
						}
						serviceUri=serviceUri+"%20ItemNo%20eq%20"+requestDto.getItemNo()+"and%20Accept/Reject%20eq%20"; 
						if(requestDto.getDecisionText().equals("Accept")){
							serviceUri=serviceUri+"TRUE'";	
						}
						else{
							serviceUri=serviceUri+"FALSE'";
						}
					}
					if(!ServicesUtil.isEmpty(requestDto.getComments())&&ServicesUtil.isEmpty(requestDto.getItemNo())){
						serviceUri=serviceUri+	"&Comments='"+URLEncoder.encode(requestDto.getComments() , "UTF-8")+"'";
					}

				} catch (UnsupportedEncodingException e1) {
					System.err.println("[PMC][ODataServicesUtil][actions][executeAction][error][URLEncoder] " +e1.getMessage());
					returnMessage.setMessage("URL Encoding Failed");
					return returnMessage;
				}
				serviceUri = serviceBase +serviceUri;
				//	System.err.println("[PMC][ODataServicesUtil][Xpath][actions][username]"+USERNAME+"[serviceUri] "+serviceUri);

				try {
					returnMessage = executeActionHttp(serviceUri);
					if(returnMessage.getStatus().equals("SUCCESS")){
						if(requestDto.getTaskType().equals("AddComment")){
							returnMessage.setMessage("Adding Comment Successful");	
						}else{
							returnMessage.setMessage( requestDto.getTaskType() + " Successful");
						}
					}
				} catch (IOException e) {
					returnMessage.setMessage(e.getMessage());
				}
			}
			else{

				returnMessage.setMessage("BAD REQUEST");
				return returnMessage;
			}
		}
		else {
			returnMessage.setMessage("AUTHORISATION FAILED");
			return returnMessage;
		}

		return returnMessage;
	}




	public static NodeList xPathOdata(String serviceUrl, String usedFormatXml,String type ,String expression,String username,String password){
		if(!ServicesUtil.isEmpty(username) && !ServicesUtil.isEmpty(password))
		{
			USERNAME = username;
			PASSWORD = ServicesUtil.getDecryptedText(password);
		}
		NodeList nodeList = null ;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			InputStream inputFile = execute(serviceUrl,usedFormatXml, type);
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			XPath xPath =  XPathFactory.newInstance().newXPath();

			nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
		} catch (Exception e) {
			System.err.println("[PMC][ODataServicesUtil][xPathOdata][error] " +e.getMessage());
			e.printStackTrace();
		} 
		return nodeList;
	}


	public static ResponseMessage executeActionHttp(String url) throws IOException
	{       
		//	System.err.println("[PMC][ODataServicesUtil][actions][executeActionHttp][entry] " +url);
		ResponseMessage returnMessage = new ResponseMessage();
		String X_CSRF_TOKEN= "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost postRequest = new HttpPost(url);
		try{
			{
				HttpGet httpget = new HttpGet(url);
				httpget.setHeader("Authorization", getBasicAuth());
				httpget.setHeader("x-csrf-token", "fetch");
				Header headers[] = httpget.getAllHeaders();
				HttpResponse res = httpclient.execute(httpget);                           
				headers = res.getAllHeaders();
				for (Header h : headers) {
					if (h.getName().equals("x-csrf-token")) {
						X_CSRF_TOKEN = h.getValue();
						//	System.err.println("[PMC][ODataServicesUtil][actions][executeActionHttp][csrf] " +X_CSRF_TOKEN);
					}
				}
			}

			// The main POST REQUEST

			postRequest.addHeader("Authorization", getBasicAuth());
			
			//   PASSING THE TOKEN GOTTEN FROM THE CODE ABOVE
			postRequest.setHeader("x-csrf-token", X_CSRF_TOKEN); 

			HttpResponse response = httpclient.execute(postRequest);
			String result = EntityUtils.toString(response.getEntity());
			int responseCode = response.getStatusLine().getStatusCode();

			//	System.err.println("Http Response: Response code " + responseCode);
			returnMessage.setStatusCode(Integer.toString(responseCode));
			if(responseCode!=200 && result.toString().contains("error")){
				try {
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc;
					InputStream stream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8.name()));
					doc = dBuilder.parse(stream);
					doc.getDocumentElement().normalize();
					XPath xPath =  XPathFactory.newInstance().newXPath();
					XPathExpression expr1=xPath.compile("error/message/text()"); 
					NodeList node1 = (NodeList) expr1.evaluate(doc, XPathConstants.NODESET);
					returnMessage.setMessage(node1.item(0).getNodeValue());
					returnMessage.setStatus("FAILURE");

				} catch (Exception e1) {
					System.err.println("[PMC][ODataServicesUtil][actions][executeActionHttp][togetError]"+e1.getMessage());
				}
			}
			else{
				returnMessage.setStatus("SUCCESS");
			}
		}catch(Exception e ){
			System.err.println("[PMC][ODataServicesUtil][actions][executeActionHttp][error]"+e.getMessage());
			returnMessage.setMessage(e.getMessage());
			returnMessage.setStatus("FAILURE");
			returnMessage.setStatusCode("1");
		}
	//	System.err.println("[PMC][ODataServicesUtil][actions][executeActionHttp][exit]"+returnMessage);
		return returnMessage;
	}

	private static String getBasicAuth() {
		String userpass = USERNAME + ":" + PASSWORD;
		return "Basic "
		+ javax.xml.bind.DatatypeConverter.printBase64Binary(userpass
				.getBytes());
	}





	/*	
	 * UNCOMMENT TO GET ONLY THE COUNT OF THE ATTACHMENTS OR COMMENTS 
	 * 
	 * public static String getCountInString(String sapOrigin, String instanceId,String type){
	String serviceUri = "http://sthcigwdq1.kaust.edu.sa:8005/sap/opu/odata/IWPGW/TASKPROCESSING;mo;v=2/TaskCollection(SAP__Origin='"+sapOrigin+"',InstanceID='"+instanceId+"')/"+type+"/$count";
	HttpURLConnection connection = null;
	String st= "";
	try{
		connection = initializeConnection(serviceUri,  PMCConstant.APPLICATION_XML, PMCConstant.HTTP_METHOD_GET);
		connection.connect();
		checkStatus(connection);
		InputStream content = connection.getInputStream();
		byte[] buffer = streamToArray(content);
		st = new String(buffer);

	}catch(Exception e){
		System.err.println("[PMC][ODataServicesUtil][getCountInString][error]  "+ e.getMessage());
	}	
	finally{
		if(!ServicesUtil.isEmpty(connection)){
			connection.disconnect();
		}
	}
	return st;
} */


}