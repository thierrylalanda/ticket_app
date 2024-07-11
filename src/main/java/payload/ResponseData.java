/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payload;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author LALANDA
 */
@Data
@Builder
public class ResponseData<T extends Object> {

    private boolean success;
    private String message;
    private T data;

}

