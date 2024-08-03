package xyz.ibudai.validate.core.handler;


import xyz.ibudai.validate.common.context.ValidateContext;

public interface Checker {

    /**
     * To validate the object field
     *
     * @param context context
     */
    void check(ValidateContext context) throws Exception;
}
