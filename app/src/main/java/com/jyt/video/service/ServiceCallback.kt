package com.jyt.video.service


class ServiceCallback<T> constructor( val result:(code:Int, data:T?) -> Unit)


