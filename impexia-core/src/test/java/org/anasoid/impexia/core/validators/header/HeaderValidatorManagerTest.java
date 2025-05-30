package org.anasoid.impexia.core.validators.header;

import java.util.List;
import org.anasoid.impexia.core.internal.spi.register.AbstractRegistrator;
import org.anasoid.impexia.core.internal.spi.register.RegistratorAgent;
import org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptorEnum;
import org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptorEnumGlobal;
import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.exceptions.header.ActionException;
import org.anasoid.impexia.meta.exceptions.header.AttributeModifierException;
import org.anasoid.impexia.meta.exceptions.header.ImpexHeaderException;
import org.anasoid.impexia.meta.header.ImpexAction;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.header.ImpexModifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */

class HeaderValidatorManagerTest {

  private static final String INVALID_MODIFIER_MESSAGE =
      "custom attribute accepted only when Class modifier is used";
  private static final String INVALID_MODIFIER_MODE_MESSAGE = "is not acceptable by Mode";
  private static final String INVALID_MODIFIER_LEVEL_MESSAGE = "is not acceptable by Level";
  private static final String INVALID_MODIFIER_BOOLEAN_MESSAGE = "should be boolean";
  private static HeaderValidatorManager headerValidator;

  @BeforeAll
  static void init() {
    headerValidator = new HeaderValidatorManager();
    AbstractRegistrator registrator =
        new AbstractRegistrator() {
          @Override
          protected RegistratorAgent<ModifierDescriptorEnum> getModifierDescriptorAgent() {
            return null;
          }
        };
    registrator.load();
  }

  @Test
  void failInvalidModifierHeader() throws ImpexHeaderException {
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .modifier(ImpexModifier.builder("uniq", "tr").build())
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(ImpexModifier.builder("unique", "true").build())
                    .build())
            .build();

    try {
      headerValidator.validate(impexHeader, Mode.IMPORT);
      Assertions.fail("Import not valid modifier accepted");
    } catch (AttributeModifierException e) {
      Assertions.assertTrue(e.getMessage().contains(INVALID_MODIFIER_MESSAGE), e.getMessage());
    }
    try {
      headerValidator.validate(impexHeader, Mode.EXPORT);
      Assertions.fail("Import not valid modifier accepted");
    } catch (AttributeModifierException e) {
      Assertions.assertTrue(e.getMessage().contains(INVALID_MODIFIER_MESSAGE), e.getMessage());
    }
  }

  @Test
  void failInvalidModifierAttribute() throws ImpexHeaderException {
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .modifier(ImpexModifier.builder("uniq", "tr").build())
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(ImpexModifier.builder("unique", "true").build())
                    .build())
            .build();

    try {
      headerValidator.validate(impexHeader, Mode.IMPORT);
      Assertions.fail("Import not valid modifier accepted");
    } catch (AttributeModifierException e) {
      Assertions.assertTrue(e.getMessage().contains(INVALID_MODIFIER_MESSAGE), e.getMessage());
    }
    try {
      headerValidator.validate(impexHeader, Mode.EXPORT);
      Assertions.fail("Import not valid modifier accepted");
    } catch (AttributeModifierException e) {
      Assertions.assertTrue(e.getMessage().contains(INVALID_MODIFIER_MESSAGE), e.getMessage());
    }
  }

  @Test
  void failInvalidModifierAttributeMulti() throws ImpexHeaderException {

    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifiers(
                        List.of(
                            ImpexModifier.builder("uniq", "tr").build(),
                            ImpexModifier.builder(
                                    ModifierDescriptorEnumGlobal.UNIQUE.toString(), "true")
                                .build()))
                    .build())
            .build();

    try {
      headerValidator.validate(impexHeader, Mode.IMPORT);
      Assertions.fail("Import not valid modifier accepted");
    } catch (AttributeModifierException e) {
      Assertions.assertTrue(e.getMessage().contains(INVALID_MODIFIER_MESSAGE), e.getMessage());
    }
    try {
      headerValidator.validate(impexHeader, Mode.EXPORT);
      Assertions.fail("Import not valid modifier accepted");
    } catch (AttributeModifierException e) {
      Assertions.assertTrue(e.getMessage().contains(INVALID_MODIFIER_MESSAGE), e.getMessage());
    }
  }

  @Test
  void validCustomModifierAttribute() throws ImpexHeaderException {
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifiers(
                        List.of(
                            ImpexModifier.builder("uniq", "tr").build(),
                            ImpexModifier.builder("unique", "true").build(),
                            ImpexModifier.builder(
                                    ModifierDescriptorEnumGlobal.TRANSLATOR.toString(), "tr")
                                .build()))
                    .build())
            .build();

    headerValidator.validate(impexHeader, Mode.IMPORT);
  }

  @Test
  void validCustomModifierHeader() throws ImpexHeaderException {
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .modifiers(
                List.of(
                    ImpexModifier.builder(
                            ModifierDescriptorEnumGlobal.ERRORHANDLER.toString(), "tr")
                        .build(),
                    ImpexModifier.builder("uniq", "tr").build()))
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(ImpexModifier.builder("unique", "true").build())
                    .build())
            .build();

    headerValidator.validate(impexHeader, Mode.IMPORT);
  }

  @Test
  void validateModifierLevelAttribute() throws ImpexHeaderException {

    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(
                        ImpexModifier.builder(
                                ModifierDescriptorEnumGlobal.UNIQUE.toString().toString(), "true")
                            .build())
                    .modifier(ImpexModifier.builder("pathDelimiter", ":").build())
                    .build())
            .build();

    headerValidator.validate(impexHeader, Mode.IMPORT);
  }

  @Test
  void validateModifierBoolean() throws ImpexHeaderException {
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(
                        ImpexModifier.builder(
                                ModifierDescriptorEnumGlobal.UNIQUE.toString(), "true")
                            .build())
                    .build())
            .build();

    headerValidator.validate(impexHeader, Mode.IMPORT);

    impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(
                        ImpexModifier.builder(
                                ModifierDescriptorEnumGlobal.UNIQUE.toString(), "false")
                            .build())
                    .build())
            .build();

    headerValidator.validate(impexHeader, Mode.IMPORT);
  }

  @Test
  void validateModifierBooleanFail() throws ImpexHeaderException {
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(
                        ImpexModifier.builder(ModifierDescriptorEnumGlobal.UNIQUE.toString(), "tr")
                            .build())
                    .build())
            .build();

    try {
      headerValidator.validate(impexHeader, Mode.IMPORT);
      Assertions.fail("boolean check fail");
    } catch (AttributeModifierException e) {
      Assertions.assertTrue(
          e.getMessage().contains(INVALID_MODIFIER_BOOLEAN_MESSAGE), e.getMessage());
    }
  }

  @Test
  void validateModifierMode() throws ImpexHeaderException {
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(
                        ImpexModifier.builder(
                                ModifierDescriptorEnumGlobal.MANDATORY.toString(), "true")
                            .build())
                    .build())
            .build();

    try {
      headerValidator.validate(impexHeader, Mode.EXPORT);
      Assertions.fail("Unique not valid for export");
    } catch (AttributeModifierException e) {
      Assertions.assertTrue(e.getMessage().contains(INVALID_MODIFIER_MODE_MESSAGE), e.getMessage());
    }
  }

  @Test
  void validateModifierLevel() throws ImpexHeaderException {
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .modifier(
                ImpexModifier.builder(ModifierDescriptorEnumGlobal.UNIQUE.toString(), "true")
                    .build())
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(
                        ImpexModifier.builder(
                                ModifierDescriptorEnumGlobal.UNIQUE.toString(), "true")
                            .build())
                    .build())
            .build();

    try {
      headerValidator.validate(impexHeader, Mode.IMPORT);
      Assertions.fail("Unique not valid for header");
    } catch (AttributeModifierException e) {
      Assertions.assertTrue(
          e.getMessage().contains(INVALID_MODIFIER_LEVEL_MESSAGE), e.getMessage());
    }
  }

  @Test
  void validateModifierAction() throws ImpexHeaderException {
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", null)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(
                        ImpexModifier.builder(
                                ModifierDescriptorEnumGlobal.UNIQUE.toString(), "true")
                            .build())
                    .build())
            .build();

    try {
      headerValidator.validate(impexHeader, Mode.IMPORT);
      Assertions.fail("Action can't be null");
    } catch (ActionException e) {
      // nothing
    }
  }
}
