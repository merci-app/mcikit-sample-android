package kit.merci.sample

import android.app.Activity
import android.content.Context
import android.widget.Toast
import kit.merci.provider.ClientProvider

class SampleClientProvider : ClientProvider() {

    override fun authenticate() {

    }

    override fun supportRequested(context: Context) {
        (context as? Activity)?.run {
            Toast.makeText(this, "Paraibanas Support!!!!", Toast.LENGTH_LONG).show()
        }
    }

}