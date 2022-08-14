package in.ashokit.binding;

import javax.persistence.Column;

import lombok.Data;

@Data
public class State {
	private Integer stateId;

	private String stateName;

	private Integer countryId;
}
