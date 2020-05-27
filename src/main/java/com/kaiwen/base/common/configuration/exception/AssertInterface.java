package com.kaiwen.base.common.configuration.exception;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * The interface Assert.
 *
 * @author maofs
 * @version 1.0
 * @date 2020 -05-09 10:22:18
 */
public interface AssertInterface {
    
    /**
     * State.
     *
     * @param expression the expression
     * @param message the message
     */
    default void state(boolean expression, String message) {
        if (!expression) {
            throw new CustomException(message);
        }
    }
    
    /**
     * State.
     *
     * @param message the message
     */
    default void state(String message) {
        throw new CustomException(message);
    }
    
    /**
     * State.
     *
     * @param expression the expression
     * @param messageSupplier the message supplier
     */
    default void state(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new CustomException(nullSafeGet(messageSupplier));
        }
    }
    
    /**
     * Is true.
     *
     * @param expression the expression
     * @param message the message
     */
    default void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new CustomException(message);
        }
    }
    
    /**
     * Is true.
     *
     * @param expression the expression
     * @param messageSupplier the message supplier
     */
    default void isTrue(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new CustomException(nullSafeGet(messageSupplier));
        }
    }
    
    /**
     * Is null.
     *
     * @param object the object
     * @param message the message
     */
    default void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new CustomException(message);
        }
    }
    
    /**
     * Is null.
     *
     * @param object the object
     * @param messageSupplier the message supplier
     */
    default void isNull(@Nullable Object object, Supplier<String> messageSupplier) {
        if (object != null) {
            throw new CustomException(nullSafeGet(messageSupplier));
        }
    }
    
    /**
     * Not null.
     *
     * @param object the object
     * @param message the message
     */
    default void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new CustomException(message);
        }
    }

    /**
     * Not null.
     *
     * @param str String str
     * @param message the message
     */
    default void notNull(@Nullable String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new CustomException(message);
        }
    }
    
    /**
     * Not null.
     *
     * @param object the object
     * @param messageSupplier the message supplier
     */
    default void notNull(@Nullable Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new CustomException(nullSafeGet(messageSupplier));
        }
    }
    
    /**
     * Equal.
     *
     * @param var1 the var 1
     * @param var2 the var 2
     * @param msg the msg
     */
    default void isEqual(@NotEmpty String var1, @NotEmpty String var2, String msg) {
        if (!var1.equals(var2)) {
            throw new CustomException(StringUtils.isEmpty(msg) ? "参数不等" : msg);
        }
    }
    /**
     * notEqual.
     *
     * @param var1 the var 1
     * @param var2 the var 2
     * @param msg the msg
     */
    default void notEqual(@NotEmpty String var1, @NotEmpty String var2, String msg) {
        if (var1.equals(var2)) {
            throw new CustomException(StringUtils.isEmpty(msg) ? "参数相等" : msg);
        }
    }
    
    /**
     * Has length.
     *
     * @param text the text
     * @param message the message
     */
    default void hasLength(@Nullable String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new CustomException(message);
        }
    }
    
    /**
     * Has length.
     *
     * @param text the text
     * @param messageSupplier the message supplier
     */
    default void hasLength(@Nullable String text, Supplier<String> messageSupplier) {
        if (!StringUtils.hasLength(text)) {
            throw new CustomException(nullSafeGet(messageSupplier));
        }
    }
    
    /**
     * Has text.
     *
     * @param text the text
     * @param message the message
     */
    default void hasText(@Nullable String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new CustomException(message);
        }
    }
    
    /**
     * Has text.
     *
     * @param text the text
     * @param messageSupplier the message supplier
     */
    default void hasText(@Nullable String text, Supplier<String> messageSupplier) {
        if (!StringUtils.hasText(text)) {
            throw new CustomException(nullSafeGet(messageSupplier));
        }
    }
    
    /**
     * Does not contain.
     *
     * @param textToSearch the text to search
     * @param substring the substring
     * @param message the message
     */
    default void doesNotContain(@Nullable String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring)
            && textToSearch.contains(substring)) {
            throw new CustomException(message);
        }
    }
    
    /**
     * Does not contain.
     *
     * @param textToSearch the text to search
     * @param substring the substring
     * @param messageSupplier the message supplier
     */
    default void doesNotContain(@Nullable String textToSearch, String substring, Supplier<String> messageSupplier) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring)
            && textToSearch.contains(substring)) {
            throw new CustomException(nullSafeGet(messageSupplier));
        }
    }
    
    /**
     * Not empty.
     *
     * @param array the array
     * @param message the message
     */
    default void notEmpty(@Nullable Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new CustomException(message);
        }
    }
    
    /**
     * Not empty.
     *
     * @param array the array
     * @param messageSupplier the message supplier
     */
    default void notEmpty(@Nullable Object[] array, Supplier<String> messageSupplier) {
        if (ObjectUtils.isEmpty(array)) {
            throw new CustomException(nullSafeGet(messageSupplier));
        }
    }
    
    /**
     * No null elements.
     *
     * @param array the array
     * @param message the message
     */
    default void noNullElements(@Nullable Object[] array, String message) {
        if (array != null) {
            int var3 = array.length;
            
            for (Object element : array) {
                if (element == null) {
                    throw new CustomException(message);
                }
            }
        }
        
    }
    
    /**
     * No null elements.
     *
     * @param array the array
     * @param messageSupplier the message supplier
     */
    default void noNullElements(@Nullable Object[] array, Supplier<String> messageSupplier) {
        if (array != null) {
            
            for (Object element : array) {
                if (element == null) {
                    throw new CustomException(nullSafeGet(messageSupplier));
                }
            }
        }
        
    }
    
    /**
     * Not empty.
     *
     * @param collection the collection
     * @param message the message
     */
    default void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new CustomException(message);
        }
    }
    
    /**
     * Not empty.
     *
     * @param collection the collection
     * @param messageSupplier the message supplier
     */
    default void notEmpty(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new CustomException(nullSafeGet(messageSupplier));
        }
    }
    
    /**
     * Not empty.
     *
     * @param map the map
     * @param message the message
     */
    default void notEmpty(@Nullable Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new CustomException(message);
        }
    }
    /**
     * Not empty.
     *
     * @param object o
     * @param message the message
     */
    default void notEmpty(@Nullable Object object, String message) {
        if (object == null || "".equals(object)) {
            throw new CustomException(message);
        }
    }
    
    /**
     * Not empty.
     *
     * @param map the map
     * @param messageSupplier the message supplier
     */
    default void notEmpty(@Nullable Map<?, ?> map, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(map)) {
            throw new CustomException(nullSafeGet(messageSupplier));
        }
    }
    
    /**
     * Is instance of.
     *
     * @param type the type
     * @param obj the obj
     * @param message the message
     */
    default void isInstanceOf(Class<?> type, @Nullable Object obj, String message) {
        notNull(type, (String)"Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, message);
        }
        
    }
    
    /**
     * Is instance of.
     *
     * @param type the type
     * @param obj the obj
     * @param messageSupplier the message supplier
     */
    default void isInstanceOf(Class<?> type, @Nullable Object obj, Supplier<String> messageSupplier) {
        notNull(type, (String)"Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, nullSafeGet(messageSupplier));
        }
        
    }
    
    /**
     * Is instance of.
     *
     * @param type the type
     * @param obj the obj
     */
    default void isInstanceOf(Class<?> type, @Nullable Object obj) {
        isInstanceOf(type, obj, "");
    }
    
    /**
     * Is assignable.
     *
     * @param superType the super type
     * @param subType the sub type
     * @param message the message
     */
    default void isAssignable(Class<?> superType, @Nullable Class<?> subType, String message) {
        notNull(superType, "Super type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, message);
        }
        
    }
    
    /**
     * Is assignable.
     *
     * @param superType the super type
     * @param subType the sub type
     * @param messageSupplier the message supplier
     */
    default void isAssignable(Class<?> superType, @Nullable Class<?> subType, Supplier<String> messageSupplier) {
        notNull(superType, (String)"Super type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, nullSafeGet(messageSupplier));
        }
        
    }
    
    /**
     * Is assignable.
     *
     * @param superType the super type
     * @param subType the sub type
     */
    default void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }
    
    /**
     * Instance check failed.
     *
     * @param type the type
     * @param obj the obj
     * @param msg the msg
     */
    default void instanceCheckFailed(Class<?> type, @Nullable Object obj, @Nullable String msg) {
        String className = obj != null ? obj.getClass().getName() : "null";
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, className);
                defaultMessage = false;
            }
        }
        
        if (defaultMessage) {
            result = result + "Object of class [" + className + "] must be an instance of " + type;
        }
        
        throw new CustomException(result);
    }
    
    /**
     * Assignable check failed.
     *
     * @param superType the super type
     * @param subType the sub type
     * @param msg the msg
     */
    default void assignableCheckFailed(Class<?> superType, @Nullable Class<?> subType, @Nullable String msg) {
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, subType);
                defaultMessage = false;
            }
        }
        
        if (defaultMessage) {
            result = result + subType + " is not assignable to " + superType;
        }
        
        throw new CustomException(result);
    }
    
    /**
     * Ends with separator boolean.
     *
     * @param msg the msg
     * @return the boolean
     */
    default boolean endsWithSeparator(String msg) {
        return msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith(".");
    }
    
    /**
     * Message with type name string.
     *
     * @param msg the msg
     * @param typeName the type name
     * @return the string
     */
    default String messageWithTypeName(String msg, @Nullable Object typeName) {
        return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
    }
    
    /**
     * Null safe get string.
     *
     * @param messageSupplier the message supplier
     * @return the string
     */
    @Nullable
    default String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
        return messageSupplier != null ? (String)messageSupplier.get() : null;
    }
}
