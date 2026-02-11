package timetracker.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController{
	@GetMapping("/health")
	public String health(){
		return "OK! Rpi5 er oppe og går via TailScale\n";
	}
}
