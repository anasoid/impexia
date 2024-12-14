package org.anasoid.impexia.meta.modifier;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.anasoid.impexia.meta.Mode;

/**
 * Modifier builder.
 *
 * @see Modifier
 */
public class ModifierBuilder {

  private final String code;
  private Set<Mode> modes = EnumSet.allOf(Mode.class);
  private Set<Level> levels;
  private Set<BasicType> basicTypes = EnumSet.allOf(BasicType.class);
  private Set<GroupType> groupTypes = EnumSet.allOf(GroupType.class);
  private Class<?> clazz;
  private Set<String> values;
  private final String scope;
  private boolean needMapping;

  public ModifierBuilder(String code, String scope) {
    this.code = code;
    this.scope = scope;
  }

  public ModifierBuilder setModes(Mode... modes) {
    this.modes = Arrays.asList(modes).stream().collect(Collectors.toSet());
    return this;
  }

  public ModifierBuilder setNeedMapping(boolean needMapping) {
    this.needMapping = needMapping;
    return this;
  }

  public ModifierBuilder setLevels(Level... levels) {
    this.levels = Arrays.asList(levels).stream().collect(Collectors.toSet());
    return this;
  }

  public ModifierBuilder setBasicTypes(BasicType... basicTypes) {
    this.basicTypes = Arrays.asList(basicTypes).stream().collect(Collectors.toSet());
    return this;
  }

  public ModifierBuilder setGroupTypes(GroupType... groupTypes) {
    this.groupTypes = Arrays.asList(groupTypes).stream().collect(Collectors.toSet());
    ;
    return this;
  }

  public ModifierBuilder setClazz(Class<?> clazz) {
    this.clazz = clazz;
    return this;
  }

  public ModifierBuilder setValues(String... values) {
    this.values = Arrays.asList(values).stream().collect(Collectors.toSet());
    return this;
  }

  public ModifierBuilder setValues(Set<String> values) {
    this.values = values;
    return this;
  }

  public Modifier build() {
    return new Modifier(
        code, modes, levels, basicTypes, groupTypes, clazz, values, needMapping, scope);
  }
}
