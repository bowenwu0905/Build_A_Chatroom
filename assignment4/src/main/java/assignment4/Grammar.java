package assignment4;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * generate a random sentence
 * @author xiaochong and zitao
 */
public class Grammar {

  private static final String regex = "(?<=\\<).*?(?=\\>)";

  /**
   * create a new construct
   */
  public Grammar() {
  }

  /**
   * random select a word from TreeMap
   * @param attribute String from attribute select word
   * @param rules TreeMap
   * @return String selected word
   * @throws Exception UndefinedTerminalException and Exception
   */
  public String textGenerator(String attribute, TreeMap<String, List<String>> rules) throws Exception {
    if (rules.isEmpty()) {
      throw new UndefinedTerminalException();
    }

    // whether json have the attribute
    if (rules.containsKey(attribute)) {
      // random generate an index and get its value of attribute
      List<String> values = rules.get(attribute);
      String value = values.get(numberGenerator(values.size()));
      String afterReplace = value;
      // find pattern
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(value);
      // recursively find attributes
      while (matcher.find()) {
        String replaced = '<' + matcher.group() + '>';
        afterReplace = afterReplace.replaceAll(replaced, textGenerator(matcher.group(), rules));
      }
      return afterReplace;
    } else {
      throw new UndefinedTerminalException("Json don't have the " + attribute);
    }
  }

  /**
   * generate random number from upper bound
   * @param size upper bound
   * @return a random number
   */
  public int numberGenerator(int size) {
    Random random = new Random();
    return random.nextInt(size);
  }

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash
   * tables such as those provided by {@link HashMap}.
   * <p>
   * The general contract of {@code hashCode} is:
   * <ul>
   * <li>Whenever it is invoked on the same object more than once during
   *     an execution of a Java application, the {@code hashCode} method
   *     must consistently return the same integer, provided no information
   *     used in {@code equals} comparisons on the object is modified.
   *     This integer need not remain consistent from one execution of an
   *     application to another execution of the same application.
   * </ul>
   *
   * @return a hash code value for this object.
   * {@code Object} returns distinct integers for distinct objects.
   * @see Object#equals(Object)
   * @see System#identityHashCode
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   * <p>
   * The {@code equals} method implements an equivalence relation on non-null object references:
   * <ul>
   * <li>It is <i>reflexive</i>: for any non-null reference value
   *     {@code x}, {@code x.equals(x)} should return
   *     {@code true}.
   * <li>It is <i>symmetric</i>: for any non-null reference values
   *     {@code x} and {@code y}, {@code x.equals(y)}
   *     should return {@code true} if and only if
   *     {@code y.equals(x)} returns {@code true}.
   * <li>It is <i>transitive</i>: for any non-null reference values
   *     {@code x}, {@code y}, and {@code z}, if
   *     {@code x.equals(y)} returns {@code true} and
   *     {@code y.equals(z)} returns {@code true}, then
   *     {@code x.equals(z)} should return {@code true}.
   * <li>It is <i>consistent</i>: for any non-null reference values
   *     {@code x} and {@code y}, multiple invocations of
   *     {@code x.equals(y)} consistently return {@code true}
   *     or consistently return {@code false}, provided no
   *     information used in {@code equals} comparisons on the
   *     objects is modified.
   * <li>For any non-null reference value {@code x},
   *     {@code x.equals(null)} should return {@code false}.
   * </ul>
   *
   * <p>
   * An equivalence relation partitions the elements it operates on
   * into <i>equivalence classes</i>; all the members of an
   * equivalence class are equal to each other. Members of an
   * equivalence class are substitutable for each other, at least
   * for some purposes.
   *
   * @param obj the reference object with which to compare.
   * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
   * and {@code y}, this method returns {@code true} if and only if {@code x} and {@code y} refer to
   * the same object ({@code x == y} has the value {@code true}).
   * <p>
   * In other words, under the reference equality equivalence relation, each equivalence class only
   * has a single element.
   * @see #hashCode()
   * @see HashMap
   */
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return "Grammar{}";
  }
}
