import React, { InputHTMLAttributes } from 'react'
import classes from './Input.module.scss'
type InputProps = InputHTMLAttributes<HTMLInputElement> & {
    label? : string,
    size? : "small" | "medium" | "large",
}
export default function Input({label,size,width, ...otherProps} : InputProps) {
  return (
    <div className={`${classes.root} ${classes[size || "large"]}`}>
        <label>{label}</label>
        <input
          style={{
            width: width? `${width}px` : "100%"
          }} 
          {...otherProps}></input>
    </div>
  )
}
