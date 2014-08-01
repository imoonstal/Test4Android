package com.imoonstal.invokewcf;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

	static String NameSpace = "http://tempuri.org/";
	static String URL="http://10.0.2.2:8733/WCFLibrary/TestService/";
	static String SOAP_ACTION="http://tempuri.org/ITestService/GetString";
	static String MethodName="GetString";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void CallWebService(){
    	//指定WebService的命名空间和调用的方法名 
    	SoapObject request = new SoapObject(NameSpace, MethodName);
    	
    	//设置需调用WebService接口需要传入的参数
    	request.addProperty("value", "imoonstal");
    	
    	// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);  
  
        // 下面这两句是一样的作用 
        envelope.bodyOut = request;         
        envelope.setOutputSoapObject(request);
        
        // 设置是否调用的是dotNet开发的WebService  
        envelope.dotNet = true;  
  
        HttpTransportSE transport = new HttpTransportSE(URL);  
        try {  
            // 调用WebService  
            transport.call(SOAP_ACTION, envelope);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        // 获取返回的数据  
        SoapObject object = (SoapObject) envelope.bodyIn; 
        if(null==object){
        	return;
        }
        // 获取返回的结果  
        String result = object.getProperty(0).toString();
        
        System.out.println(result);
    }
    
    
    public void btnClick(View v){
    	new Thread(runnable).start();
    }
    
    Runnable runnable = new Runnable(){

		@Override
		public void run() {
			
			CallWebService();
		}
    	
    };
}
