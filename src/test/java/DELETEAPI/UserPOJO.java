package DELETEAPI;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPOJO {	
	private Integer id;
	private String name;
	private String email;
	private String gender;
	private String status;
	
	public UserPOJO(String name, String email, String gender, String status) {		
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.status = status;
	}
	
	
}
