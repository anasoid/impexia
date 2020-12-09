package org.anasoid.impexia.meta.modifier;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.anasoid.impexia.meta.Mode;

/**
 * Modifier builder.
 *
 * @see Modifier
 */
public class ModifierBuilder {

  private final String code;
  private Set<Mode> modes = new HashSet<>(Arrays.asList(Mode.values()));
  private Set<Level> levels;
  private Set<BasicType> basicTypes = new HashSet<>(Arrays.asList(BasicType.values()));
  private Set<GroupType> groupTypes = new HashSet<>(Arrays.asList(GroupType.values()));
  private Class<?> clazz;
  private Set<String> values;
  private final String scope;

  public ModifierBuilder(String code, String scope) {
    this.code = code;
    this.scope = scope;
  }

  public ModifierBuilder setModes(Mode... modes) {
    this.modes = new HashSet<>(Arrays.asList(modes));
    return this;
  }

  public ModifierBuilder setLevels(Level... levels) {
    this.levels = new HashSet<>(Arrays.asList(levels));
    return this;
  }

  public ModifierBuilder setBasicTypes(BasicType... basicTypes) {
    this.basicTypes = new HashSet<>(Arrays.asList(basicTypes));
    return this;
  }

  public ModifierBuilder setGroupTypes(GroupType... groupTypes) {
    this.groupTypes = new HashSet<>(Arrays.asList(groupTypes));
    return this;
  }

  public ModifierBuilder setClazz(Class<?> clazz) {
    this.clazz = clazz;
    return this;
  }

  public ModifierBuilder setValues(String... values) {
    this.values = new HashSet<>(Arrays.asList(values));
    return this;
  }

  public ModifierBuilder setValues(Set<String> values) {
    this.values = values;
    return this;
  }

  public Modifier build() {
    return new Modifier(code, modes, levels, basicTypes, groupTypes, clazz, values, scope);
  }
}
