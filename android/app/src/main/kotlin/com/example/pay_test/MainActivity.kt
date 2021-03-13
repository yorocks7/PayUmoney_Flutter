package com.example.pay_test

import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import com.payumoney.core.PayUmoneyConfig
import com.payumoney.core.PayUmoneyConstants
import com.payumoney.core.PayUmoneySdkInitializer
import com.payumoney.core.entity.TransactionResponse
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager
import com.payumoney.sdkui.ui.utils.ResultModel
import io.flutter.plugin.common.MethodCall;


class MainActivity: FlutterActivity() {

    private val CHANNEL = "PayUmoney";

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->
            if (call.method == "initPayment") {
                try {
                    callPayUMoneySdk(call)
                    result.success("payment successful")

                } catch (e: Exception) {
                    result.error("unsuccessful", e.message, null)
                }
            } else {
                result.notImplemented()
            }
        }
    }

    private fun callPayUMoneySdk(@NonNull call: MethodCall){
        val builder: PayUmoneySdkInitializer.PaymentParam.Builder = PayUmoneySdkInitializer.PaymentParam.Builder()

        builder.setAmount(""+call.argument("amount"))
                .setTxnId(""+call.argument("txnid"))
                .setPhone(""+call.argument("phone"))
                .setProductName(""+call.argument("productinfo"))
                .setFirstName(""+call.argument("firstname"))
                .setEmail(""+call.argument("email"))
                .setsUrl(""+call.argument("surl"))
                .setfUrl(""+call.argument("furl"))
                .setUdf1(""+call.argument("udf1"))
                .setUdf2(""+call.argument("udf2"))
                .setIsDebug(true)
                .setKey(""+call.argument("key"))
                .setMerchantId(""+call.argument("key"))

        val paymentParam: PayUmoneySdkInitializer.PaymentParam = builder.build()
        paymentParam.setMerchantHash(call.argument("hashh"))
        PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, this, 1, false)
    }
}
