LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := hello-jni
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/Users/allen/GitHub/AndroidDevCoder/hellojni/src/main/jni/Android.mk \
	/Users/allen/GitHub/AndroidDevCoder/hellojni/src/main/jni/Application.mk \
	/Users/allen/GitHub/AndroidDevCoder/hellojni/src/main/jni/hello-jni.c \

LOCAL_C_INCLUDES += /Users/allen/GitHub/AndroidDevCoder/hellojni/src/main/jni
LOCAL_C_INCLUDES += /Users/allen/GitHub/AndroidDevCoder/hellojni/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
