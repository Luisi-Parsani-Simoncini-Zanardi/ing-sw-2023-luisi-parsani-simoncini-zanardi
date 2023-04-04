package org.projectsw.Model.CommonGoal;

public class DiagonalTriangle extends CommonGoalStrategy{

    private boolean diag;


    public DiagonalTriangle(int code){
        super(code);
        if (code == 11) {
            this.diag = true;
        } else if (code == 12) {
            this.diag = false;
        }
    }
}
