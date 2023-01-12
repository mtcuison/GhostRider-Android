package org.guanzongroup.com.creditevaluation.Core;

import java.util.List;

public class oEvaluate {

    private final oParentFndg poParent;
    private final List<oChildFndg> poChild;

    public oEvaluate(oParentFndg foArgs, List<oChildFndg> foArgs1) {
        this.poParent = foArgs;
        this.poChild = foArgs1;
    }

    public oParentFndg getPoParent() {
        return poParent;
    }

    public List<oChildFndg> getPoChild() {
        return poChild;
    }
}
