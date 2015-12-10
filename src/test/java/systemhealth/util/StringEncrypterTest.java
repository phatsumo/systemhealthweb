package systemhealth.util;
/**
 *
 */


import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 1062992
 *
 */
public class StringEncrypterTest {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(StringEncrypterTest.class);

    private StringEncrypter encrypter;

    private String cipherText;

    private String unencryptedText;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        unencryptedText = "Hello World";

        encrypter = new StringEncrypter();

        Assert.assertNotNull(encrypter);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link systemhealth.util.StringEncrypter#encrypt(java.lang.String)}.
     *
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    @Test
    public void testEncrypt() throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        cipherText = encrypter.encrypt(unencryptedText);

        LOGGER.debug("\nunencrypted text = " + unencryptedText
                + "\ncipherText is content inside [] = [" + cipherText + "]");
        Assert.assertNotNull(cipherText);

    }

    /**
     * Test method for
     * {@link systemhealth.util.StringEncrypter#decrypt(java.lang.String)}.
     *
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    @Test
    public void testDecrypt() throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        if (cipherText == null || cipherText.isEmpty()) {
            cipherText = encrypter.encrypt(unencryptedText);
        }

        String decryptedString = encrypter.decrypt(cipherText);
        LOGGER.debug("\ncipher text is content inside [] = [" + cipherText
                + "] \ndecrypted string = " + decryptedString);
        Assert.assertTrue(unencryptedText.equals(decryptedString));

    }

}
