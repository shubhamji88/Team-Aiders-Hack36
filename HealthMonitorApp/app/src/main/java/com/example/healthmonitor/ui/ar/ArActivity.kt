package com.example.healthmonitor.ui.ar

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AlertDialog
import com.example.healthmonitor.R
import com.google.ar.core.Anchor
import com.google.ar.core.ArCoreApk
import com.google.ar.core.ArCoreApk.Availability
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.core.exceptions.UnavailableException
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.SkeletonNode
import com.google.ar.sceneform.animation.ModelAnimator
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.ar_activity.*

class ArActivity : AppCompatActivity() {
    lateinit var arFragment:ArFragment
    private lateinit var model :Uri
    private var renderable:ModelRenderable?=null
    private var animator:ModelAnimator?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isARCoreSupportedAndUpToDate()
        setContentView(R.layout.ar_activity)
        arFragment= fragment_ar as ArFragment
        model=Uri.parse("model.sfb")
        arFragment.setOnTapArPlaneListener{ hitResult: HitResult, plane: Plane, motionEvent: MotionEvent ->
            if(plane.type!=Plane.Type.HORIZONTAL_UPWARD_FACING){
                return@setOnTapArPlaneListener
            }
            var anchor=hitResult.createAnchor()
            placeObject(arFragment,anchor,model)
        }
    }


    private fun placeObject(arFragment: ArFragment, anchor: Anchor?, model: Uri?) {
        ModelRenderable.builder()
            .setSource(arFragment.context,model)
            .build()
            .thenAccept {
                renderable=it
                addToScene(arFragment,anchor,it)
            }
            .exceptionally {
                val builder=AlertDialog.Builder(this)
                    .setMessage(it.message).setTitle("Error")
                val dialog=builder.create()
                    .show()
                return@exceptionally null
            }
    }

    private fun addToScene(arFragment: ArFragment, anchor: Anchor?, it: ModelRenderable?) {
        val anchorNode= AnchorNode(anchor)
        val skeletonNode=SkeletonNode()
        skeletonNode.renderable=renderable
        val node=TransformableNode(arFragment.transformationSystem)
        node.addChild(skeletonNode)
        node.setParent(anchorNode)
        arFragment.arSceneView.scene.addChild(anchorNode)
    }

    // Verify that ARCore is installed and using the current version.
    fun isARCoreSupportedAndUpToDate(): Boolean {
        return when (ArCoreApk.getInstance().checkAvailability(this)) {
            Availability.SUPPORTED_INSTALLED -> true
            Availability.SUPPORTED_APK_TOO_OLD, Availability.SUPPORTED_NOT_INSTALLED -> {
                try {
                    // Request ARCore installation or update if needed.
                    when (ArCoreApk.getInstance().requestInstall(this, true)) {
                        ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                            Log.i("Main", "ARCore installation requested.")
                            false
                        }
                        ArCoreApk.InstallStatus.INSTALLED -> true
                    }
                } catch (e: UnavailableException) {
                    Log.e("Main", "ARCore not installed", e)
                    false
                }
            }

            Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE ->
                // This device is not supported for AR.
                false

            Availability.UNKNOWN_CHECKING -> {
                false
                // ARCore is checking the availability with a remote query.
                // This function should be called again after waiting 200 ms to determine the query result.
            }
            Availability.UNKNOWN_ERROR, Availability.UNKNOWN_TIMED_OUT -> {
                false
                // There was an error checking for AR availability. This may be due to the device being offline.
                // Handle the error appropriately.
            }
        }
    }

}