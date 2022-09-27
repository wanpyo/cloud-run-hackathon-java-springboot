package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
@RestController
public class Application {

  static class Self {
    public String href;
  }

  static class Links {
    public Self self;
  }

  static class PlayerState {
    public Integer x;
    public Integer y;
    public String direction;
    public Boolean wasHit;
    public Integer score;
  }

  static class Arena {
    public List<Integer> dims;
    public Map<String, PlayerState> state;
  }

  static class ArenaUpdate {
    public Links _links;
    public Arena arena;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.initDirectFieldAccess();
  }

  @GetMapping("/")
  public String index() {
    return "Let the battle begin!";
  }

  @PostMapping("/**")
  public String index(@RequestBody ArenaUpdate arenaUpdate) {
//    System.out.println(arenaUpdate);
    String[] commands = new String[]{"T", "T", "L", "T"};
    int i = new Random().nextInt(4);
	String command = commands[i];

    // TODO add your implementation here to replace the random response.
	PlayerState myState = new PlayerState();
	myState = (PlayerState)arenaUpdate.arena.state.get("https://cloud-run-hackathon-java-springboot-yzqf6q4kqq-uc.a.run.app");

    if (myState.wasHit) return "T";
	
	List<Integer> myDim = arenaUpdate.arena.dims;
	
	if (myState.direction.equals("W")) {
		if ( myState.x > 0 ) return "F";
		return "L";
	}

	//System.out.println("Dim x = " + myDim.get(0) + ", Dim y = " + myDim.get(1) );
	System.out.println("x = " + myState.x + ", y = " + myState.y );
	System.out.println("Direction = " + myState.direction );
	
    return commands[i];
  }

}

