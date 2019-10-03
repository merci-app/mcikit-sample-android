package kit.merci.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kit.merci.MCICallback
import kit.merci.MarketPay
import kit.merci.Merchant
import kit.merci.Merci
import kit.merci.exceptions.MerchantNotFound
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartMarketPay.setOnClickListener {
            Merci.launch(this, MarketPay)
        }

        btnMerciAuth.setOnClickListener {
            Merci.authenticate("39840279823", object : MCICallback {
                override fun onSuccess() {
                    Toast.makeText(this@MainActivity, "Auth Success", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@MainActivity, "Auth Error", Toast.LENGTH_SHORT).show()
                }
            })
        }

        btnMerciLogout.setOnClickListener {
            Merci.revokeAuthentication()
        }

        btnUberMerchant.setOnClickListener {
            try {

                Merci.launch(this, Merchant("ebbc0be2-7ced-4428-8883-c3896847a996"))


            } catch (e: MerchantNotFound) {
                Toast.makeText(this@MainActivity, "MerchantNotFound", Toast.LENGTH_SHORT).show()
            }
        }

        btnInvalidMerchant.setOnClickListener {
            try {
                Merci.launch(this, Merchant("12345"))
            } catch (e: MerchantNotFound) {
                Toast.makeText(this@MainActivity, "MerchantNotFound", Toast.LENGTH_SHORT).show()
            }

            Merci.launch(this, Merchant("12345"))
        }

    }
}
