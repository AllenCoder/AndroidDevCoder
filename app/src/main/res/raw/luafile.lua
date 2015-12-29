
function functionInLuaFile(key)
	 return 'call from android: I am in Lua file . Return : '..key..'!'
end


function callAndroidApi(context,layout,tip)
	tv = luajava.newInstance("android.widget.TextView",context)
	tv:setText(tip)
	layout:addView(tv)
end