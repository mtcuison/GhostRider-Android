package org.guanzongroup.com.creditevaluation.Adapter;

public interface onSelectResultListener {
    void OnUpdate(String fsKey, onValidateListener listener);
    void OnCorrect(String fsPar, String fsKey, String fsRes, onEvaluate listener);
    void OnIncorrect(String fsPar, String fsKey, String fsRes, onEvaluate listener);
}
