package frc.robot.Helpers;

import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.Sendable;

public class ShuffleboardHelpers{
     /**
   * Creates a {@code SimpleWidget}.
   * @param tabName the name of the shuffleboard
   * @param widgetName the name of the widget
   * @param defaultValue the default value of the widget
   * @return the {@code SimpleWidget} created
   */
  public static SimpleWidget createSimpleWidget(String tabName, String widgetName, Object defaultValue){
    SimpleWidget widget = Shuffleboard.getTab(tabName)
      .add(widgetName, defaultValue);
    Shuffleboard.update();
    return widget;
  }

  /**
   * Creates a {@code ComplexWidget}.
   * @param tabName the name of the shuffleboard
   * @param widgetName the name of the widget
   * @param sendable the object that sends data for the widget
   */
  public static void createComplexWidget(String tabName, String widgetName, Sendable sendable){
    Shuffleboard.getTab(tabName)
      .add(widgetName, sendable);
  }

  /**
   * Finds the simple widget that matches the description
   * @param tabname the shuffleboard the widget is located
   * @param widgetName the name of the widget
   * @return the {@code SimpleWidget} matching the shuffleboard and name given
   * @throws IllegalStateException if the widget could not be found
   */
  private static SimpleWidget getSimpleWidget(String tabName, String widgetName){
    int indexOfWidget = Shuffleboard.getTab(tabName)
      .getComponents()
      .size();
    for(int i = 0; i <= indexOfWidget; i++){
      if(Shuffleboard.getTab(tabName).getComponents().get(i).getTitle() == widgetName){ 
        return (SimpleWidget) Shuffleboard.getTab(tabName).getComponents().get(i);
      }
    }
    throw new IllegalStateException();
  }

  /**
   * Returns an {@code Object} of the value in the named widget.
   * @param tabName the shuffleboard the widget is located
   * @param widgetName the name of the widget
   * @return the value of the widget
   * @throws ClassCastException if the widget does not contain a {@code boolean}, 
   * {@code double}, or {@code String}
   */
  public static Object getWidgetValue(String tabName, String widgetName){
    SimpleWidget widget = getSimpleWidget(tabName, widgetName);
    if(widget.getEntry().getValue().isBoolean()){
      return widget.getEntry().getBoolean(false);
    } else if(widget.getEntry().getValue().isDouble()){
      return widget.getEntry().getDouble(0);
    } else if(widget.getEntry().getValue().isString()){
      return widget.getEntry().getString("");
    } else{
      throw new ClassCastException();
    }
  }

  /**
   * Sets the value of a widget to the specfied value.
   * @param tabName the shuffleboard the widget is located
   * @param widgetName the name of the widget
   * @param value the new value for the widget
   * @throws ClassCastException if the type of the specified value is not the same as that of the widget,
   * or the widget does not have type {@code boolean}, {@code double}, or {@code String}.
   */
  public static void setWidgetValue(String tabName, String widgetName, Object value){
    SimpleWidget widget = getSimpleWidget(tabName, widgetName);
    if(widget.getEntry().getValue().isBoolean()){
      try {
        widget.getEntry().setBoolean((boolean) value);
      } catch (Exception e) {
        System.out.println("Did not get expected type of Boolean: " + e);
      }
      
    } else if(widget.getEntry().getValue().isDouble()){
      try {
        widget.getEntry().setDouble(((Number) value).doubleValue());
      } catch (Exception e) {
        System.out.println("Did not get expected type of Number: " + e);
      }
    } else if(widget.getEntry().getValue().isString()){
      try {
        widget.getEntry().setString((String) value);
      } catch (Exception e) {
        System.out.println("Did not get expected type of String: " + e);
      }
    } else{
      throw new ClassCastException();
    }
  }
}