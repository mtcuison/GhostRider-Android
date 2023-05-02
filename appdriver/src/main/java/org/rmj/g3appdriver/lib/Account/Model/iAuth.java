package org.rmj.g3appdriver.lib.Account.Model;

public interface iAuth {

    /**
     *
     * @param params pass object base on what is being required by the class
     * @return returns 0 if process failed and 1 if process succeed.
     *          2 if other special condition occurs.
     *          Ex. Account activate
     */
    int DoAction(Object params);

    String getMessage();
}
