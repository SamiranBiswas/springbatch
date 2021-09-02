package com.capgemini.springbatch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class InMemReader implements ItemReader {
    Integer[] intArray ={1,2,3,4,5,6,7,8,9,10};
    List<Integer> myList = Arrays.asList(intArray);

    int index =0;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Integer nextItem = null;
        if ( index < myList.size()){
            nextItem = myList.get(index);
            index++;
        }else{
            index= 0;
        }
        return nextItem;
    }
}
