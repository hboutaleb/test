/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyTheatre.DAO;

/**
 *
 * @author imaddari
 */
public class test {

    public static void main(String[] args) {
        int i, j, k;
        j = 1;
        k = 5;
        i = 1000;

        for (k = 1; k < 51; k++) {
            for (j = 1; j < 21; j++) {
                System.out.println(
                        "INSERT INTO GROUPE3.LESTICKETS (NOSERIE, NOSPEC, DATEREP, NOPLACE, NORANG, DATEEMISSION, NODOSSIER) VALUES(NOSERIE_SEQ.nextval, 1 , TO_DATE('2017-03-09 08:30:00', 'YYYY-MM-DD HH24:MI:SS')," + j + " , " + k + ", TO_DATE('2017-02-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 15);");

                i++;
            }
        }
    }
}
