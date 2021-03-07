import java.util.*;
import android.os.Bundle;
import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.plugin.common.MethodChannel;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

public class MainActivity extends FlutterActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this.getFlutterEngine());
  }

  private static final String CHANNEL = "PayUmoney";

  @Override
  public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
  super.configureFlutterEngine(flutterEngine);
    new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
        .setMethodCallHandler(
          (call, result) -> {
            System.out.print("payment initiated.");

            if(call.method.equals("initPayment")){
                try {
                PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                    PayUmoneySdkInitializer.PaymentParam.Builder();
                
                builder.setAmount(call.argument("amount"))                     
                .setTxnId(call.argument("txnid"))                              
                .setPhone(call.argument("phone"))                              
                .setProductName(call.argument("productinfo"))                   
                .setFirstName(call.argument("firstname"))                         
                .setEmail(call.argument("email"))                              
                .setsUrl(call.argument("surl"))  
                .setfUrl(call.argument("furl"))
                .setUdf1(call.argument("udf1"))
                .setUdf2(call.argument("udf2"))
                .setIsDebug(true)      
                .setKey("abc")                         
                .setMerchantId("def");

                PayUmoneySdkInitializer.PaymentParam paymentParam = builder.build();
                paymentParam.setMerchantHash(call.argument("hashh"));

                PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam,
                this, 1, false);

                result.success("payment successful");
                }
                catch (Exception e){
                    result.error("unsuccessful",e.getMessage(),null);
                }

            }
            // else
            // result.notImplemented();

          }
        );
  }

}