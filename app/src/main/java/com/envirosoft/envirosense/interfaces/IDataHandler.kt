package com.envirosoft.envirosense.interfaces

import java.io.InputStream
import java.io.OutputStream

interface IDataHandler<T> {
  fun putData(outputStream: OutputStream, data: MutableList<T>)

  fun getData(inputStream: InputStream): MutableList<T>
}