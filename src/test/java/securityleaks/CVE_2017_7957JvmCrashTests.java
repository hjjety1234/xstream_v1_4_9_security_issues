package securityleaks;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.ForbiddenClassException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Use case (http://x-stream.github.io/CVE-2017-7957.html)
 * --------
 * Pass-in an element for void and watch the app crashes.
 *
 * Fix
 * ---
 * Since v1.4.7, we can force XStream to throw exception to alert on such occasions instead of crashing.
 *
 * Since v1.4.10, XStream would throw exception automatically, instead of crashing, in case encounters such deserialization use cases.
 * However, it broke http://x-stream.github.io/CVE-2013-7285.html again, so v1.4.11 had to be released. In case you are still on a
 * version less than JDKV8, then you will have to pick the hotfix version v1.4.11.1.
 */
public class CVE_2017_7957JvmCrashTests {

    @Test
    @DisplayName("On success, it should crash JVM")
    void injectVoidThroughClassAttributeWhenDeserializeToPojoWhileNotSpecifiedDenyTypes() {

        XStream xstream = new XStream();

        assertThrows(Error.class,
                     ()->{
                         xstream.fromXML("<string class='void'>Hello, world!</string>");
                     });
    }
}
