package com.team1091.shared.components

import com.team1091.shared.system.IGrabberSystem

enum class MouthState(
        val gotoExtendedClosed: (grabberSystem: IGrabberSystem) -> MouthState,
        val gotoExtendedOpen: (grabberSystem: IGrabberSystem) -> MouthState,
        val gotoWithdrawnClosed: (grabberSystem: IGrabberSystem) -> MouthState,
        val gotoWithdrawnOpen: (grabberSystem: IGrabberSystem) -> MouthState
) {
    S1ExtendedClose(
            gotoExtendedClosed = {
                grabberSystem -> S1ExtendedClose
            },
            gotoExtendedOpen = {
                grabberSystem -> grabberSystem.open()
                S1ExtendedClose
            },
            gotoWithdrawnClosed = { grabberSystem ->
                grabberSystem.withdraw()
                S1ExtendedClose
            },
            gotoWithdrawnOpen = { grabberSystem ->
                if (grabberSystem.isOpen())
                {
                    grabberSystem.withdraw()
                }else{
                    grabberSystem.open()
                }
                S1ExtendedClose
            }
    ),
    S2ExtendedOpen(
            gotoExtendedClosed = { grabberSystem ->
                grabberSystem.open()
                S2ExtendedOpen
            },
            gotoExtendedOpen = {
                S2ExtendedOpen
            },
            gotoWithdrawnClosed = { grabberSystem ->
                if (grabberSystem.isOpen())
                {
                    grabberSystem.closed()
                }else{
                    grabberSystem.withdraw()
                }
                S2ExtendedOpen
            },
            gotoWithdrawnOpen = { grabberSystem ->
                grabberSystem.withdraw()
                S2ExtendedOpen
            }
    ),
    S3WithdrawnClose(
            gotoExtendedClosed ={
                grabberSystem ->
                grabberSystem.extend()
                S3WithdrawnClose
            },
            gotoExtendedOpen = { grabberSystem ->
                if (grabberSystem.isExtended()){
                    grabberSystem.open()
                }
                else
                {
                    grabberSystem.extend()
                }
                S3WithdrawnClose
            },
            gotoWithdrawnClosed = { grabberSystem ->
                S3WithdrawnClose
            },
            gotoWithdrawnOpen = { grabberSystem ->
                grabberSystem.open()
                S3WithdrawnClose
            }
    ),
    S4WithdrawnOpen(
            gotoExtendedClosed ={
                grabberSystem ->
                if(grabberSystem.isExtended()) {
                    grabberSystem.closed()
                }else{
                    grabberSystem.extend()
                }
                S4WithdrawnOpen
            },
            gotoExtendedOpen = { grabberSystem ->
                grabberSystem.extend()
                S4WithdrawnOpen
            },
            gotoWithdrawnClosed = { grabberSystem ->
                grabberSystem.closed()
                S4WithdrawnOpen
            },
            gotoWithdrawnOpen = { grabberSystem ->
                S4WithdrawnOpen
            }
    )
}