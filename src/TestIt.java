import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestIt {
    String[] params() default {};
    String expectedValue() default "";
    Class<? extends Throwable> expectedException() default None.class;

    class None extends Throwable {
    }
}
