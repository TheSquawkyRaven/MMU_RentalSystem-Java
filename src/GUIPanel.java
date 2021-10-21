package src;

import javax.swing.*;

import src.SystemComponents.CLI;

import java.util.*;
import java.awt.*;
import java.text.NumberFormat;
import src.Property.*;

import javax.swing.table.*;

/**
 * 
 * This GUIPanel serves as a GUI container (like a page container below menu bar)
 * 
 */
public abstract class GUIPanel<T extends GUIWindow> extends JPanel implements IObservableViewable, IViewable, IPanelHandler {
    
    //This serves as a quick lookup for the Resource singleton
    public static Resource Resource(){
        return Resource.instance();
    }

    public GUIPanel<T> self(){
        return this;
    }

    public JLabel JLabel(String text){
        JLabel label = new JLabel(text);
        label.setForeground(Theme().text_color);

        return label;
    }
    public JTextField JTextField(){
        JTextField textField = new JTextField();
        setTextFieldTheme(textField);

        return textField;
    }
    public JPasswordField JPasswordField(){
        JPasswordField passwordField = new JPasswordField();
        setTextFieldTheme(passwordField);

        return passwordField;
    }
    public JFormattedTextField JFormattedTextField(NumberFormat f){
        JFormattedTextField formattedField = new JFormattedTextField(f);
        setTextFieldTheme(formattedField);

        return formattedField;
    }
    private void setTextFieldTheme(JTextField field){
        field.setBackground(Theme().background_color);
        field.setForeground(Theme().text_color);
        field.setBorder(Theme().border);

        field.setCaretColor(Theme().caret_color);
    }
    public JSpinner JSpinner(SpinnerModel model){
        JSpinner spinner = new JSpinner(model);
        spinner.setBackground(Theme().background_color);
        spinner.setForeground(Theme().text_color);
        spinner.setBorder(Theme().border);

        Component c = spinner.getEditor().getComponent(0);
        c.setBackground(Theme().background_color);
        c.setForeground(Theme().text_color);
        
        return spinner;
    }
    public <E> JComboBox<E> JComboBox(E[] types){
        JComboBox<E> combo = new JComboBox<>(types);
        combo.setBackground(Theme().background_color);
        combo.setForeground(Theme().text_color);
        
        return combo;
    }
    public JCheckBox JCheckBox(String text){
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setBackground(Theme().background_color);
        checkBox.setForeground(Theme().text_color);

        return checkBox;
    }
    public Table JTable(){
        DefaultTableModel model = new DefaultTableModel(1, 1);
        Table table = new Table(model);
        table.setBackground(Theme().background_color);
        table.setForeground(Theme().text_color);
        table.setGridColor(Theme().border_color);
        table.setSelectionBackground(Theme().button_hover_color);
        table.setSelectionBackground(Theme().sub_background_color);
        table.setSelectionForeground(Theme().sub_text_color);

        JTableHeader headerModel = table.getTableHeader();
        headerModel.setBackground(Theme().sub_background_color);
        headerModel.setForeground(Theme().sub_text_color);
        
        return table;
    }
    public JScrollPane JScrollPane(Component o){
        JScrollPane scrollPane = new JScrollPane(o);
        scrollPane.setBackground(Theme().background_color);
        scrollPane.setForeground(Theme().text_color);
        scrollPane.setBorder(Theme().border);

        JViewport viewport = scrollPane.getViewport();
        viewport.setBackground(Theme().background_color);
        viewport.setForeground(Theme().text_color);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setBackground(Theme().sub_background_color);

        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setBackground(Theme().sub_background_color);

        return scrollPane;
    }

    private void setButtonTheme(Button button){
        button.setBackground(Theme().background_color);
        button.setForeground(Theme().text_color);
        button.setPressedColor(Theme().button_pressed_color);
        button.setHoverColor(Theme().button_hover_color);
        button.setBorder(Theme().border);
        button.setFocusPainted(false);
    }
    public Button Button(String text){
        Button button = new Button(text);
        setButtonTheme(button);
        return button;
    }
    public Button Button(){
        Button button = new Button();
        setButtonTheme(button);
        return button;
    }


    public Resource.Theme Theme(){
        return parent.Theme();
    }


    public final T parent;

    public JPanel targetPanel;

    private ArrayList<GUIPanel<?>> panels = new ArrayList<GUIPanel<?>>();
    private HashMap<GUIPanel<?>, Object> panelsToLayout = new HashMap<GUIPanel<?>, Object>();
    private ArrayList<GUIPanel<?>> frozenPanels = new ArrayList<GUIPanel<?>>();

    public ArrayList<GUIPanel<?>> getPanels(){
        return panels;
    }

    public GUIPanel(T parent){
        this.parent = parent;
        setTargetPanel(this);
    }

    public void generalFont(Component comp){
        comp.setFont(Resource().general_font);
    }

    public void defaultComponentSettings(Component comp){
    }

    public void setTargetPanel(JPanel panel){
        targetPanel = panel;
    }

    @Override
    public void add(Component comp, Object constraints){
        defaultComponentSettings(comp);
        if (targetPanel instanceof GUIPanel<?> guiPanel){
            guiPanel.parentAdd(comp, constraints);
        }
        else{
            targetPanel.add(comp, constraints);
        }
    }
    @Override
    public Component add(Component comp){
        defaultComponentSettings(comp);
        if (targetPanel instanceof GUIPanel<?> guiPanel){
            return guiPanel.parentAdd(comp);
        }
        else{
            return targetPanel.add(comp);
        }
    }
    @Override
    public Component add(Component comp, int index){
        defaultComponentSettings(comp);
        if (targetPanel instanceof GUIPanel<?> guiPanel){
            return guiPanel.parentAdd(comp, index);
        }
        else{
            return targetPanel.add(comp, index);
        }
    }
    @Override
    public void add(Component comp, Object constraints, int index){
        defaultComponentSettings(comp);
        if (targetPanel instanceof GUIPanel<?> guiPanel){
            guiPanel.parentAdd(comp, constraints, index);
        }
        else{
            targetPanel.add(comp, constraints, index);
        }
    }
    @Override
    public Component add(String name, Component comp){
        defaultComponentSettings(comp);
        if (targetPanel instanceof GUIPanel<?> guiPanel){
            return guiPanel.parentAdd(name, comp);
        }
        else{
            return targetPanel.add(name, comp);
        }
    }
    private void parentAdd(Component comp, Object constraints){
        super.add(comp, constraints);
    }
    private Component parentAdd(Component comp){
        return super.add(comp);
    }
    private Component parentAdd(Component comp, int index){
        return super.add(comp, index);
    }
    private void parentAdd(Component comp, Object constraints, int index){
        super.add(comp, constraints, index);
    }
    private Component parentAdd(String name, Component comp){
        return super.add(name, comp);
    }

    @Override
    public void remove(Component comp){
        if (targetPanel instanceof GUIPanel<?> guiPanel){
            guiPanel.parentRemove(comp);
        }
        else{
            targetPanel.remove(comp);
        }
    }
    private void parentRemove(Component comp){
        super.remove(comp);
        repaint();
    }

    /**
     * Call this in onCreatePanel
     */
    @Override
    public void attachPanel(GUIPanel<?> panel){
        panels.add(panel);
        panel.onCreateInternal();
        panel.onCreatePanel();
    }
    /**
     * Does not add the panel
     * @param panel
     */
    public void executePanelLifecycle(GUIPanel<?> panel){
        panel.onCreateInternal();
        panel.onCreatePanel();
        panel.onViewInternal();
    }
    public void attachPanel(GUIPanel<?> panel, Object layout){
        attachPanel(panel);
        panelsToLayout.put(panel, layout);
    }
    public void attachFrozenPanel(GUIPanel<?> panel){
        attachPanel(panel);
        frozenPanels.add(panel);
    }
    public void attachFrozenPanel(GUIPanel<?> panel, Object layout){
        attachPanel(panel, layout);
        frozenPanels.add(panel);
    }
    /**
     * Calls destroy when detaching panel
     */
    @Override
    public void detachPanel(GUIPanel<?> panel){
        panels.remove(panel);
        panel.onDestroyInternal();
        panelsToLayout.remove(panel);
        remove(panel);
    }
    public void swapDestroyPanel(GUIPanel<?> oldPanel, GUIPanel<?> newPanel){
        detachPanel(oldPanel);
        attachPanel(newPanel);
        viewAttachedPanel(newPanel);
    }
    public void swapDestroyPanel(GUIPanel<?> oldPanel, GUIPanel<?> newPanel, String layout){
        detachPanel(oldPanel);
        attachPanel(newPanel, layout);
        viewAttachedPanel(newPanel);
    }
    /**
     * Make sure targetPanel is accurate!
     * @param freezingPanel
     * @param thawingPanel
     */
    public void switchExistingPanels(GUIPanel<?> freezingPanel, GUIPanel<?> thawingPanel){
        freezingPanel.onPreparingToFreeze();

        frozenPanels.add(freezingPanel);
        frozenPanels.remove(thawingPanel);

        remove(freezingPanel);
        freezingPanel.onFrozen();
        addAttachedPanel(thawingPanel);
        thawingPanel.onThawed();
    }

    /**
     * This is for child classes to initialize panel based values, such as size
     */
    public abstract void onCreate();
    public final void onCreateInternal(){
        defaultPanelSettings();
        onCreate();
        created();
    }

    private void defaultPanelSettings(){
        setBackground(Theme().panel_background_color);
    }

    /**
     * This is for child classes to create the panel, and attach to the view
     * Panels can attach as many panels down the hierarchy, but will cause a stack overflow if is attached together
     * Creation of panels will go down the hierarchy, then view will go down again
     */
    public abstract void onCreatePanel();
    
    /**
     * This is when everything is initialized and can view (setVisible here)
     */
    public abstract void onView();
    public final void onViewInternal(){
        onView();
        onViewAttachPanels();
        viewed();
        revalidate();
    }

    @Override
    public void onViewAttachPanels(){
        for (GUIPanel<?> panel : panels){
            viewAttachedPanel(panel);
        }
    }
    protected void viewAttachedPanel(GUIPanel<?> panel){
        //Skip adding if is frozen
        if (!frozenPanels.contains(panel)){
            addAttachedPanel(panel);
        }
        panel.onViewInternal();
    }
    private void addAttachedPanel(GUIPanel<?> panel){
        JPanel initialPanel = targetPanel;
        setTargetPanel(this);
        if (panelsToLayout.containsKey(panel)){
            Object layout = panelsToLayout.get(panel);
            add(panel, layout);
        }
        else{
            add(panel);
        }
        setTargetPanel(initialPanel);
    }

    /**
     * Panel is going to switch.
     * This is called before the new panel is created (before onCreate)
     */
    public abstract void onPreparingToFreeze();

    /**
     * Panel is switched (changed to another panel) in the navigation stack
     * This is called after the new panel is viewed (onCreate and onView)
     */
    public abstract void onFrozen();

    /**
     * Viewable is switched to this (changed to this viewable) in the navigation stack
     */
    public abstract void onThawed();

    /**
     * Panel is destroted (back button)
     */
    public abstract void onDestroy();
    public final void onDestroyInternal(){
        onDestroy();
        destroyed();
    }


    /**
     * Observer implementation
     */
    public HashSet<IObserverViewable> observers = new HashSet<IObserverViewable>();
    @Override
    public IObservableViewable getObservable(){
        return this;
    }
    @Override
    public void observe(IObserverViewable observer){
        observers.add(observer);
    }
    @Override
    public void stopObserving(IObserverViewable observer){
        observers.remove(observer);
    }
    @Override
    public void contentUpdated(){
        notifyObservers(ViewableObservableComponents.none);
    }
    @Override
    public void notifyObservers(Object o){
        
        ViewableObservableComponents c = (ViewableObservableComponents)o;
        //Command implementation
        Command<IObserverViewable> command = new Command<IObserverViewable>(){
            @Override
            public boolean execute(IObserverViewable observer){
                switch (c){
                    case none:
                        observer.contentUpdated();
                        break;
                    case creation:
                        observer.created(self());
                        break;
                    case view:
                        observer.viewed(self());
                        break;
                    case destroy:
                        observer.destroyed(self());
                        break;
                }
                observer.contentUpdated();
                return false;
            }
        };
        for (IObserverViewable observer : observers){
            command.execute(observer);
        }
    }
    @Override
    public void created(){
        notifyObservers(ViewableObservableComponents.creation);
    }
    @Override
    public void viewed(){
        notifyObservers(ViewableObservableComponents.view);
    }
    @Override
    public void destroyed(){
        notifyObservers(ViewableObservableComponents.destroy);
    }


}
