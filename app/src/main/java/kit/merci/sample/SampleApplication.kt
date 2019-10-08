package kit.merci.sample

import androidx.multidex.MultiDexApplication
import foundation.merci.external.Environment
import kit.merci.Merci

class SampleApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Merci.instantiate(
            application = this,
            clientId = "<put-here-cliendId>",
            clientSecret = "<put-here-clientSecret>",
            environment = Environment.SANDBOX,
            clientProvider = SampleClientProvider(this)
        )
    }

}