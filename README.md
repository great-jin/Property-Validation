<h1 align="center">Property Validation</h1>

`Property Validation` is an easy use and annotation base tool for Java field validation.

In the good project, the code should be focus in service logic, something like properties check is not the top priority.

So that is `Property Validation` use for, to peel off the none important thing out code, make everything graceful.


## Manual
`Property Validation` is friendly use for maven, you can see the module of `validate-test` for fully example.

### Basic use
Import the below dependency in your maven pom.xml file.
```xml
<dependency>
    <groupId>xyz.ibudai</groupId>
    <artifactId>validate-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <!-- Any version you want -->
    <version>2.0.7</version>
</dependency>
```

And then you can just and the annotation to your Java field like this:
```java
public class User {

    @NotBlank
    private String id;

    @Length(min = 2, max = 5)
    private String name;
}
```

Trigger the validation with `FieldValidate` class, for example:
```java
public class MyTest {
    @Test
    public void demo() throws Exception {
        User user = new User();
        user.setId("123");
        user.setName("Alex");
        FieldValidate.validate(user);
    }
}
```


### Group
The annotation support the operation group, so you don't have to define multiple class for difference entrance.

Here is simple example:
```java
public class User {

    @NotBlankGroup({
            @NotBlank(group = 1)
    })
    private String id;
}
```

And you can trigger the validation with specify group.
```java
public class MyTest {
    @Test
    public void demo() throws Exception {
        User user1 = new User();
        // default group is 0
        FieldValidate.validate(user1);
            
        User user2 = new User();
        user2.setId("123");
        FieldValidate.validate(user2, 1);
    }
}
```


### El expression
The annotation also support the Spring EL expression for properties bound.

Here is simple example:
```java
public class User {

    @NotBlank
    private String id;

    @NotBlank(triggered = "id == '123'")
    private String name;
}
```

```java
public class MyTest {
    @Test
    public void demo() throws Exception {
        User user1 = new User();
        user2.setId("456");
        // this will pass
        FieldValidate.validate(user1);
            
        User user2 = new User();
        user2.setId("123");
        // this will fail
        FieldValidate.validate(user2);
    }
}
```

### Exception log
The validation provide the ability to print target value when validate failed. 

Also, for sensitive field that you can declare with annotation with `@Sensitive`.

Notice: This is function by `Jackson`, compare to simply print message this may cost some efficiency.

Here is simple example:
```java
@JsonFilter(ValidateConst.FILTER_SENSITIVE)
public class User {

    @NotBlank
    private String username;
    
    @Sensitive
    @NotBlank
    private String password;
}
```

```java
public class MyTest {
    @Test
    public void demo() throws Exception {
        User user = new User();
        user.setUsername("Alex");
        FieldValidate.validate(user, true);
    }
}
```

And here is exception message, as you can see that the field of `password` didn't printed.
```txt
xyz.ibudai.validate.common.exception.ValidateException: The field of {password} can't be blank, Object: {"username":"Alex"}
```
