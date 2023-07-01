package com.Unicash.issuehandling.staticHelpers;

import com.Unicash.issuehandling.exception.OtherException;

public class staticHelper {
    public static boolean checkPaginationPossible(Integer start, Integer max, int listCount) {
//        int noOfEntityPerPage;

        int start1 = start == null || start <= 0 ? 0 : start;
        int max1 = max == null || max <= 0 ? 0 : max;//max can be the number of entities u want at a single page.

//        int start1 = start == null || start <= 1 ? 0 : start-1;//default is 0th.
//        int max1 = max == null || max <= 1 ? 1 : max+1;//default is upto-1th ... which means atleast 0th will be displayed.

        System.out.println("hellostart______" + start1 + "______" + max1 + "______" + listCount + "______");
//        if (start1 >= max1 || listCount == 0 || listCount < max1) throw new OtherException()("");
        if (start1 >= max1) throw new OtherException("pageStart must be less than pageMax");
        if (listCount == 0 ) throw new OtherException("listCount is 0");
//        if (listCount < max1) //throw new OtherException("listCount is less than pageMax");
//        {
//            int listCountModulo = listCount %(max1-start1);//remaining at last. 3?
//            max1 = start1 + max1;
//        }
        System.out.println("helloend______" + start1 + "______" + max1 + "______" + listCount + "______");

        return true;
    }
}
