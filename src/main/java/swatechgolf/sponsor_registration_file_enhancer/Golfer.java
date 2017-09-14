package swatechgolf.sponsor_registration_file_enhancer;

/**
 * @author Burt Cox (e41887)
 *
 * Description:
 * 
 * Create Date: Jun 5, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
public class Golfer {
   private String _name;
   private String _email;
   private String _shirtSize;
   public Golfer(String name, String email, String shirtSize) {
      super();
      _name = name;
      _email = email;
      _shirtSize = shirtSize;
   }
   public String getName() {
      return _name;
   }
   public void setName(String name) {
      _name = name;
   }
   public String getEmail() {
      return _email;
   }
   public void setEmail(String email) {
      _email = email;
   }
   public String getShirtSize() {
      return _shirtSize;
   }
   public void setShirtSize(String shirtSize) {
      _shirtSize = shirtSize;
   }
}
