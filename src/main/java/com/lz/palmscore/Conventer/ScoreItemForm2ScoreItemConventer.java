package com.lz.palmscore.Conventer;

import com.lz.palmscore.entity.ScoreItem;
import com.lz.palmscore.form.ScoreItemForm;

public class ScoreItemForm2ScoreItemConventer {
    public static ScoreItem conventer(ScoreItemForm scoreItemForm){
        ScoreItem scoreItem=new ScoreItem();

        scoreItem.setName(scoreItemForm.getName());
        scoreItem.setRate(scoreItemForm.getRate());
        scoreItem.setNote(scoreItem.getNote());
        scoreItem.setFileUpload(scoreItemForm.getFileUpload());
        return scoreItem;
    }
}
