package kit.merci.sample

import androidx.multidex.MultiDexApplication
import foundation.merci.external.Environment
import kit.merci.Merci

class SampleApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Merci.instantiate(
            application = this,
            clientId = "MIY32P8YNZ1_IAPQCJVXWOZS",
            clientSecret = "\$2a\$10\$wZ*pGUL38*LkfKFbKGDpZOI6mh*L44FUyLjLeDfjg7ZApY0R71BQ6",
            environment = Environment.SANDBOX,
            clientProvider = SampleClientProvider()
        )
    }

}