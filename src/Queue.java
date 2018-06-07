
import models.Element;
import java.util.LinkedList;
import java.util.List;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marianne
 */
public class Queue {
 private List<Element> elements;
 
 public Queue(){
     elements = new LinkedList<Element>();
 }
 
  public void insert(Element element) {
    this.elements.add(element);
  }

  public Element remove() {
      
    return this.elements.remove(0);
  }

  public boolean isEmpty() {
    return this.elements.isEmpty();
  }
 
}
