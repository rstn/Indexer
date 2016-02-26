/**
 * Created by boris on 25.02.2016.
 */
package com.simbirsoft.indexer.demo;

import com.simbirsoft.indexer.api.WordIndex;

import java.util.Set;

public class Demo {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: demo input_file word");
            return;
        }
        WordIndex wi = new WordIndex();
        wi.loadFile(args[0]);
        Set<Integer> result = wi.getIndexes4Word(args[1]);
        System.out.print("Positions: ");
        if (result == null) {
            System.out.println("none");
        } else {
            System.out.print(result);
        }
    }
}
