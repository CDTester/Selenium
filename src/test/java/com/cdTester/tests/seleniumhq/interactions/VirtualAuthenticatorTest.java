package com.cdTester.tests.seleniumhq.interactions;

import com.cdTester.tests.seleniumhq.BaseChromeTest;
import com.cdTester.pages.selenium.web.Urls;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.virtualauthenticator.Credential;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A representation of the Web Authenticator model.
 * Web applications can enable a public key-based authentication mechanism known as Web Authentication to authenticate users in a passwordless manner.
 * Web Authentication defines APIs that allows a user to create a public-key credential and register it with an authenticator.
 * An authenticator can be a hardware device or a software entity that stores user’s public-key credentials and retrieves them on request.
 * As the name suggests, Virtual Authenticator emulates such authenticators for testing.
 */
public class VirtualAuthenticatorTest extends BaseChromeTest {

  //  A pkcs#8 encoded encrypted RSA private key as a base64url string.
  private final static PKCS8EncodedKeySpec rsaPrivateKey = new PKCS8EncodedKeySpec(
    Base64.getMimeDecoder().decode(Urls.base64EncodedRsaPK)
  );

  // A pkcs#8 encoded unencrypted EC256 private key as a base64url string.
  private final static PKCS8EncodedKeySpec ec256PrivateKey = new PKCS8EncodedKeySpec(
    Base64.getUrlDecoder().decode(Urls.base64EncodedEC256PK)
  );

  @Test
  @DisplayName("Should be able to set virtual authenticator options")
  public void testVirtualOptions() {
    VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
            .setIsUserVerified(true)
            .setHasUserVerification(true)
            .setIsUserConsenting(true)
            .setTransport(VirtualAuthenticatorOptions.Transport.USB)
            .setProtocol(VirtualAuthenticatorOptions.Protocol.U2F)
            .setHasResidentKey(false);

    assertEquals(6, options.toMap().size());
  }

  @Test
  @DisplayName("Should be able to create a virtual authenticator using U2F protocol")
  public void testCreateAuthenticator() {
    // Create a virtual authenticator with U2F protocol and non-resident key
    VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
            .setProtocol(VirtualAuthenticatorOptions.Protocol.U2F)
            .setHasResidentKey(false);

    VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver)
            .addVirtualAuthenticator(options);

    // no credential have been added yet
    List<Credential> credentialList = authenticator.getCredentials();
    assertEquals(0, credentialList.size());
  }

  @Test
  @DisplayName("Should be able to remove a virtual authenticator")
  public void testRemoveAuthenticator() {
    // Create a virtual authenticator with default options
    VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions();
    VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver)
            .addVirtualAuthenticator(options);

    // Remove the virtual authenticator
    ((HasVirtualAuthenticator) driver).removeVirtualAuthenticator(authenticator);

    assertThrows(InvalidArgumentException.class, authenticator::getCredentials);
  }

  @Test
  @DisplayName("Should be able to create a resident (stateful) credential using CTAP2 protocol")
  public void testCreateAndAddResidentialKey() {
    byte[] credentialId = {1, 2, 3, 4};
    byte[] userHandle = {1};

    // Create a virtual authenticator with resident key support using CTAP2 protocol
    VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
            .setProtocol(VirtualAuthenticatorOptions.Protocol.CTAP2)
            .setHasResidentKey(true)
            .setHasUserVerification(true)
            .setIsUserVerified(true);
    VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver)
            .addVirtualAuthenticator(options);

    // Add a resident credential
    Credential residentCredential = Credential.createResidentCredential(
            credentialId,
            "localhost",
            rsaPrivateKey,
            userHandle,
            0
    );
    authenticator.addCredential(residentCredential);

    // Verify that the credential was added
    List<Credential> credentialList = authenticator.getCredentials();
    assertEquals(1, credentialList.size());

    // verify that the credential ID matches
    Credential credential = credentialList.get(0);
    assertArrayEquals(credentialId, credential.getId());
  }

  @Test
  @DisplayName("Should be able to add a resident (stateless) credential using U2F protocol")
  public void testAddResidentCredentialNotSupportedWhenAuthenticatorUsesU2FProtocol() {
    byte[] credentialId = {1, 2, 3, 4};
    byte[] userHandle = {1};
    PKCS8EncodedKeySpec privateKey = new PKCS8EncodedKeySpec(Base64.getUrlDecoder().decode(Urls.base64EncodedEC256PK));

    // Create a virtual authenticator with resident key support using U2F protocol
    VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
            .setProtocol(VirtualAuthenticatorOptions.Protocol.U2F)
            .setHasResidentKey(true);

    VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver)
            .addVirtualAuthenticator(options);


    // Add a resident credential using a EC256 private key
    Credential credential = Credential.createResidentCredential(
            credentialId,
            "localhost",
            privateKey,
            userHandle,
            0
    );

    assertThrows(InvalidArgumentException.class, () -> authenticator.addCredential(credential));
  }

  @Test
  @DisplayName("Should be able to get credential ID")
  public void testCreateAndAddNonResidentialKey() {
    byte[] credentialId = {1, 2, 3, 4};

    // Create a virtual authenticator with non-resident key support using U2F protocol
    VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
            .setProtocol(VirtualAuthenticatorOptions.Protocol.U2F)
            .setHasResidentKey(false);

    VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver)
            .addVirtualAuthenticator(options);

    // Add a non-resident credential using a EC256 private key
    Credential nonResidentCredential = Credential.createNonResidentCredential(
            credentialId,
            "localhost",
            ec256PrivateKey,
            0
    );
    authenticator.addCredential(nonResidentCredential);

    // Verify that the credential was added
    List<Credential> credentialList = authenticator.getCredentials();
    assertEquals(1, credentialList.size());

    // verify that the credential ID matches
    Credential credential = credentialList.get(0);
    assertArrayEquals(credentialId, credential.getId());
  }

  @Test
  @DisplayName("Should be able to get private key")
  public void testGetCredential() {
    byte[] credentialId = {1, 2, 3, 4};
    byte[] userHandle = {1};

    // Create a virtual authenticator with resident key support using CTAP2 protocol
    VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
            .setProtocol(VirtualAuthenticatorOptions.Protocol.CTAP2)
            .setHasResidentKey(true)
            .setHasUserVerification(true)
            .setIsUserVerified(true);
    VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver)
            .addVirtualAuthenticator(options);

    // Add a resident credential using an RSA private key
    Credential residentCredential = Credential.createResidentCredential(
            credentialId,
            "localhost",
            rsaPrivateKey,
            userHandle, 0
    );
    authenticator.addCredential(residentCredential);

    // Verify that the credential was added
    List<Credential> credentialList = authenticator.getCredentials();
    assertEquals(1, credentialList.size());

    // verify that the credential ID
    Credential credential = credentialList.get(0);
    assertArrayEquals(credentialId, credential.getId());

    // verify that the private key matches
    assertArrayEquals(rsaPrivateKey.getEncoded(), credential.getPrivateKey().getEncoded());
  }

  @Test
  @DisplayName("Should be able to remove a credential")
  public void testRemoveCredential() {
    byte[] credentialId = {1, 2, 3, 4};

    // Create a virtual authenticator with non-resident key support using U2F protocol
    VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver)
            .addVirtualAuthenticator(new VirtualAuthenticatorOptions());

    // add a credential using an RSA private key
    Credential credential = Credential.createNonResidentCredential(
            credentialId,
            "localhost",
            rsaPrivateKey,
            0
    );
    authenticator.addCredential(credential);
    assertEquals(1, authenticator.getCredentials().size());

    // Remove the credential
    authenticator.removeCredential(credentialId);
    assertEquals(0, authenticator.getCredentials().size());
  }

  @Test
  @DisplayName("Should be able to remove all credential")
  public void testRemoveAllCredentials() {
    byte[] credentialId1 = {1, 2, 3, 4};
    byte[] credentialId2 = {1, 2, 3, 4};


    // Crate a virtual authenticator with default options
    VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver)
            .addVirtualAuthenticator(new VirtualAuthenticatorOptions());

    // add a credential using an RSA private key
    Credential residentCredential1 = Credential.createNonResidentCredential(
            credentialId1,
            "localhost",
            rsaPrivateKey,
            0
    );
    authenticator.addCredential(residentCredential1);

    // add a credential using an RSA private key
    Credential residentCredential2 = Credential.createNonResidentCredential(
            credentialId2,
            "localhost",
            rsaPrivateKey,
            0
    );
    authenticator.addCredential(residentCredential1);

    // Verify that the credential was added
    assertEquals(2, authenticator.getCredentials().size());

    // Remove all credentials
    authenticator.removeAllCredentials();
    assertEquals(0, authenticator.getCredentials().size());
  }

  @Test
  @DisplayName("Should be to set the authenticator to simulate successful user verification")
  public void testSetUserVerified() {
    VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
            .setIsUserVerified(true);

    assertTrue((boolean) options.toMap().get("isUserVerified"));
  }
}