package _data_type;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class InsuranceTypes {

	private final SimpleIntegerProperty id;
	private final SimpleStringProperty code;
	private final SimpleDoubleProperty ratio;
	private CheckBox checkbox;

	public InsuranceTypes(int i, String string, double d) {
		super();
		this.id = new SimpleIntegerProperty(i);
		this.code = new SimpleStringProperty (string);
		this.ratio = new SimpleDoubleProperty(d);
		this.checkbox = new CheckBox(); 
	}

	public final SimpleIntegerProperty idProperty() {
		return this.id;
	}

	public final int getId() {
		return this.idProperty().get();
	}

	public final void setId(final int id) {
		this.idProperty().set(id);
	}

	public final SimpleStringProperty codeProperty() {
		return this.code;
	}

	public final String getCode() {
		return this.codeProperty().get();
	}

	public final void setCode(final String code) {
		this.codeProperty().set(code);
	}

	public final SimpleDoubleProperty ratioProperty() {
		return this.ratio;
	}

	public final double getRatio() {
		return this.ratioProperty().get();
	}

	public final void setRatio(final double ratio) {
		this.ratioProperty().set(ratio);
	}

	public CheckBox getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(CheckBox checkbox) {
		this.checkbox = checkbox;
	}
	// useful when making a new contrat and we want to disable types of the company
	public void Disable()
	{
		checkbox.setDisable(true);
	}
	public void untick()
	{
		checkbox.setSelected(false);
	}

	@Override
	public String toString() {
		return "InsuranceTypes [id=" + id + ", code=" + code + ", ratio=" + ratio + ", checkbox=" + checkbox + "]";
	}

}
