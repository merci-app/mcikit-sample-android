package kit.merci.sample

import android.app.Activity
import android.content.Context
import android.widget.Toast
import kit.merci.MCICallback
import kit.merci.Merci
import kit.merci.provider.ClientProvider

class SampleClientProvider(private val context: Context) : ClientProvider() {

    override fun authenticate() {
        Merci.authenticate("<put-here-user-cpf>", object : MCICallback {
            override fun onSuccess() {
                Toast.makeText(context, "Auth Success", Toast.LENGTH_SHORT).show()
            }

            override fun onError(e: Throwable) {
                Toast.makeText(context, "Auth Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun supportRequested(context: Context) {
        (context as? Activity)?.run {
            Toast.makeText(this, "Paraibanas Support!!!!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onEvent(name: String, params: Map<String, Any>) {
    }

}