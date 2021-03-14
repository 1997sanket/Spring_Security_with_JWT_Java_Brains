/**
 * 
 */
package com.kamble.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 1997s
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

	private String username;
	private String password;
}
