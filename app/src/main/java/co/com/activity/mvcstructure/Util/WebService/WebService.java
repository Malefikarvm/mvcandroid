package co.com.activity.mvcstructure.Util.WebService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Analista on 14/09/2015.
 */
public class WebService {

    private int nueva_version = 0;
    private JSONObject jsonObj;
    private JSONArray jsonArray;
    private String UrlNamespace = "http://ecar.activitymovil.net/";
    private String UrlService = "http://ecar.activitymovil.net/WebService/web_service_recibirNew.php?wsdl";



    public boolean validateService() {
        String SOAP_ACTION = "pollServer";
        String METHOD_NAME = "pollServer";
        String NAMESPACE = UrlNamespace;
        String URL = UrlService;
        SoapObject soapclient = new SoapObject(NAMESPACE, METHOD_NAME);
        SoapObject parameters = new SoapObject(NAMESPACE, METHOD_NAME);
        parameters.addProperty("value", "testService");

        soapclient.addProperty(METHOD_NAME, parameters);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soapclient);
        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
        httpTransportSE.debug = true;
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            System.out.println("EL SERVICIO ENTRÓ CORRECTAMENTE: " + envelope.getResponse().toString());
        } catch (IOException e) {
            System.out.println("ERROR EN EL SERVICIO IO: " + e);
            return false;
        } catch (XmlPullParserException e) {
            System.out.println("ERROR EN EL SERVICIO XML: " + e);
            return false;
        }
        return true;
    }
}
