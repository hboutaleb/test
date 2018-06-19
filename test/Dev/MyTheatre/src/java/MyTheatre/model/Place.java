/*
 * Copyright (C) 2017 Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package MyTheatre.model;

/**
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
public class Place {



    /**
     * le rang de la place dans la carte
     */
    private final int rangCarte;

    /**
     * la position de la place dans le rang sur la carte
     */
    private final int colonneCarte;
    


    /**
     * 
     * @param noPlace le numéro de la place (dans la BD)
     * @param noRang   le rang (dans la BD)
     * @param noZone la zone (dans la BD)
     */
    public Place(int noPlace, int noRang, int noZone) {
        
        this.rangCarte = noRang;
        this.colonneCarte = (noPlace <= 10)?noPlace + 1:noPlace + 3;
    }

    public int getRangCarte() {
        return rangCarte;
    }

    public int getColonneCarte() {
        return colonneCarte;
    }



}
