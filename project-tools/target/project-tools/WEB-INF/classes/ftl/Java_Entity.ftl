package ${modelPack};

import java.util.Date;
import java.io.Serializable;

/**
 * ${tableDesc}
 */
public class ${modelName} implements Cloneable,Serializable{

	private static final long serialVersionUID = 1L;

	<#list fields as field>
	/**
	 * ${field.remark}
	 */
	private ${field.propertyType} ${field.propertyName};
	
	</#list>
	
	<#list fields as field>



	<#if field.propertyType=="Date" >
    public void set${field.propertyNameGS}(${field.propertyType} ${field.propertyName}) {
    	Date date = null;
		if(${field.propertyName} != null){
			date = (Date)${field.propertyName}.clone();
		}
    	this.${field.propertyName} = date;
    }

	public ${field.propertyType} get${field.propertyNameGS} () {
		Date date = null;
		if(this.${field.propertyName} != null){
			date = (Date)this.${field.propertyName}.clone();
		}
		return date;
	}
	<#else >
    public void set${field.propertyNameGS}(${field.propertyType} ${field.propertyName}) {
    	this.${field.propertyName} = ${field.propertyName};
    }

	public ${field.propertyType} get${field.propertyNameGS} () {
		return ${field.propertyName};
	}
	</#if>
	</#list>


	public ${modelName} clone(){
		${modelName} entity = null;
		try{
			entity = (${modelName})super.clone();
		}catch(CloneNotSupportedException e) {

		}
		return entity;
	}


}