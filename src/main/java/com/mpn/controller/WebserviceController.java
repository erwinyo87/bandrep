package com.mpn.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpn.model.Employee;
import com.mpn.model.User;
import com.mpn.objectmodel.ValueOnly;
import com.mpn.security.JwtUtil;
import com.mpn.services.EmployeeService;
import com.mpn.services.UserService;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import com.mpn.services.LogService;

/**
 * @author Ernest
 * @since Sept, 2016
 */
@EnableGlobalMethodSecurity
@RestController
public class WebserviceController {

    Logger logger = LoggerFactory.getLogger(WebserviceController.class);

    @Autowired
    EmployeeService empService;
    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;
//	LogService logserv;

    /**
     * @param index
     * @return String
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex() {
        return "Spring-boot has working!!!";
    }

    /**
     * @param findAll
     * @return list of employee
     * @throws JsonProcessingException
     */
    @RequestMapping(value = RestURI.GET_ALL_EMP, method = RequestMethod.GET, produces = "application/json")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Employee> getAll(Employee employee) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Employee> list = empService.findAll();
        String JSONString = mapper.writeValueAsString(list);
        JSONArray arr = new JSONArray(JSONString);

        /**
         * get JSONString data by String(key) looping JSON array
         */
        int size = arr.length();
        for (int i = 0; i < size; i++) {
            JSONObject obj = arr.getJSONObject(i);
            logger.info("Index of array-" + i + " " + obj.toString() + "," + " " + "name: " + obj.getString("name")
                    + "," + " " + "ID:" + obj.getInt("id"));
        }

        logger.info("Return Data: " + JSONString);
        return list;
    }

    /**
     * @param findByName
     * @return name of employee
     * @throws JsonProcessingException
     */
    @RequestMapping(value = RestURI.GET_BY_NAME, method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getEmployeeByName(@RequestParam String name) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(empService.findByNameWithIgnoreCase(name));
        logger.info("Employee Name by Name: " + jsonString);
        return empService.findByNameWithIgnoreCase(name);
    }

    /**
     * @param findByRole
     * @return role of employee
     * @throws JsonProcessingException
     */
    @RequestMapping(value = RestURI.GET_BY_ROLE, method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> getEmployeeByRole(@RequestParam String role) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(empService.findByRole(role));
        logger.info("Employee findByRole: " + jsonString);
        return empService.findByRole(role);
    }

    /**
     * @param postEmployee
     * @return json employee
     * @throws Exception
     */
//	@RequestMapping(value = RestURI.POST_ALL_EMP, method = RequestMethod.POST)
//	public void process(@RequestBody Employee employee) throws Exception {
//		ObjectMapper mapper = new ObjectMapper();
//		String JSONString = mapper.writeValueAsString(employee);
//		logger.info(JSONString);
//		JSONObject Obj = new JSONObject(JSONString);
//
//		Employee emp1 = new Employee();
//		emp1.setID(Obj.getInt("id"));
//		emp1.setAge(Obj.getInt("age"));
//		emp1.setName(Obj.getString("name"));
//		emp1.setRole(Obj.getString("role"));
//		emp1.setNationality(Obj.getString("nationality"));
//		
//		empService.saveEmployee(emp1);
//	}
    @RequestMapping(value = RestURI.POST_ALL_EMP, method = RequestMethod.POST)
    public void process(@RequestBody Employee employee) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String JSONString = mapper.writeValueAsString(employee);
        logger.info(JSONString);
        JSONObject Obj = new JSONObject(JSONString);

//		JSONArray arr = new JSONArray(JSONString);
//		int size = arr.length();
//		logger.info("Size of Array: "+size);
        Employee emp1 = new Employee();
        emp1.setID(Obj.getInt("id"));
        emp1.setAge(Obj.getInt("age"));
        emp1.setName(Obj.getString("name"));
        emp1.setRole(Obj.getString("role"));
        emp1.setNationality(Obj.getString("nationality"));

        empService.saveEmployee(emp1);
    }

    @RequestMapping(value = RestURI.POST_LOGIN, method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Object userParameter) throws ServletException, JsonProcessingException, Exception {
        ObjectMapper mapper = new ObjectMapper();
        String JSONString = mapper.writeValueAsString(userParameter);
//		logger.info(JSONString);
        logger.info("Login Parameter: " + JSONString);
        JSONObject Obj = new JSONObject(JSONString);

//        try {
//        User user = userService.login(Obj.getString("username"), Obj.getString("password"));
        User user = userService.findByUsername(Obj.getString("username"));
        logger.info("User:" + user.toString());
        if (BCrypt.checkpw(Obj.getString("password"), user.getPassword())) {

//                return user;
//            return ResponseEntity.ok(jwtUtil.generateToken(user.getUsername(), "USER"));
            return ResponseEntity.ok("OK");
        } else {
//            throw  new ServletException("Invalid Password");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        logger.info("User:" + user.toString());
//        String jsonString = mapper.writeValueAsString(userService.login(Obj.getString("username"), Obj.getString("username")));
//        return userParameter;
//        } catch (Exception E) {
//            
//        }
    }

    @RequestMapping(value = RestURI.USER_ADD, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public User userAdd(@RequestBody Object userParameter) throws JsonProcessingException, NoSuchAlgorithmException {
        ObjectMapper mapper = new ObjectMapper();
        String JSONString = mapper.writeValueAsString(userParameter);
//		logger.info(JSONString);
        logger.info("Login Parameter: " + JSONString);
        JSONObject Obj = new JSONObject(JSONString);

//        try {
//        User user = userService.login(Obj.getString("username"), Obj.getString("password"));
        User user = new User();
        user.setName(Obj.getString("name"));
        user.setUsername(Obj.getString("username"));
        user.setPassword(Obj.getString("password"));
        userService.saveUser(user);
        logger.info("User:" + user.toString());
        return user;
//        logger.info("User:" + user.toString());
//        String jsonString = mapper.writeValueAsString(userService.login(Obj.getString("username"), Obj.getString("username")));
//        return userParameter;
//        } catch (Exception E) {
//            
//        }

    }

    @RequestMapping(value = RestURI.GET_ALL_USR, method = RequestMethod.GET, produces = "application/json")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<User> getAllUser() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<User> list = userService.findAll();
        String JSONString = mapper.writeValueAsString(list);
        JSONArray arr = new JSONArray(JSONString);

        /**
         * get JSONString data by String(key) looping JSON array
         */
        int size = arr.length();
        for (int i = 0; i < size; i++) {
            JSONObject obj = arr.getJSONObject(i);
            logger.info("Index of array-" + i + " " + obj.toString() + "," + " " + "name: " + obj.getString("name")
                    + "," + " " + "ID:" + obj.getInt("userId"));
        }

        logger.info("Return Data: " + JSONString);
        return list;
    }

    @RequestMapping(value = RestURI.CALCSUM, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ValueOnly getCalculatedSum(@RequestBody List<Object> objList) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Object> list = objList;
        String JSONString = mapper.writeValueAsString(list);
        JSONArray arr = new JSONArray(JSONString);

        /**
         * get JSONString data by String(key) looping JSON array
         */
        int sum = 0;
        int size = arr.length();
        for (int i = 0; i < size; i++) {
            JSONObject obj = arr.getJSONObject(i);
            logger.info("Index of array-" + i + " " + obj.toString()
                    + "," + " " + "Value:" + obj.getInt("value"));
            sum += obj.getInt("value");
        }

        logger.info("Return Data: " + JSONString);
//        JSONObject returnJson = new JSONObject();
//        returnJson.put("value", sum);
//        Object a = returnJson;
        ValueOnly vO = new ValueOnly(sum);
        return vO;
    }
}
