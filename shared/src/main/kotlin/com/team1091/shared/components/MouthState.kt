package com.team1091.shared.components

import com.team1091.shared.system.IGrabberSystem

enum class MouthState(
        val gotoExtendedClosed: (grabberSytstem: IGrabberSystem) -> MouthState,
        val gotoExtendedOpen: (grabberSytstem: IGrabberSystem) -> MouthState,
        val gotoWithdrawnClosed: (grabberSytstem: IGrabberSystem) -> MouthState,
        val gotoWithdrawnOpen: (grabberSytstem: IGrabberSystem) -> MouthState
) {
    S1ExtendedClose(
            gotoExtendedClosed = {
                grabberSytstem -> S1ExtendedClose
            },
            gotoExtendedOpen = {
                grabberSytstem -> grabberSytstem.open()
                S1ExtendedClose
            },
            gotoWithdrawnClosed = { grabberSytstem ->
                grabberSytstem.withdraw()
                S1ExtendedClose
            },
            gotoWithdrawnOpen = { grabberSytstem ->
                if (grabberSytstem.isOpen())
                {
                    grabberSytstem.withdraw()
                }else{
                    grabberSytstem.open()
                }
                S1ExtendedClose
            }
    ),
    S2ExtendedOpen(
            gotoExtendedClosed = { grabberSytstem ->
                grabberSytstem.open()
                S2ExtendedOpen
            },
            gotoExtendedOpen = {
                S2ExtendedOpen
            },
            gotoWithdrawnClosed = { grabberSytstem ->
                if (grabberSytstem.isOpen())
                {
                    grabberSytstem.closed()
                }else{
                    grabberSytstem.withdraw()
                }
                S2ExtendedOpen
            },
            gotoWithdrawnOpen = { grabberSytstem ->
                grabberSytstem.withdraw()
                S2ExtendedOpen
            }
    ),
    S3WithdrawnClose(
            gotoExtendedClosed ={
                grabberSytstem ->
                grabberSytstem.extend()
                S3WithdrawnClose
            },
            gotoExtendedOpen = { grabberSytstem ->
                if (grabberSytstem.isExtended()){
                    grabberSytstem.open()
                }
                else
                {
                    grabberSytstem.extend()
                }
                S3WithdrawnClose
            },
            gotoWithdrawnClosed = { grabberSytstem ->
                S3WithdrawnClose
            },
            gotoWithdrawnOpen = { grabberSytstem ->
                grabberSytstem.open()
                S3WithdrawnClose
            }
    ),
    S4WithdrawnOpen(
            gotoExtendedClosed ={
                grabberSytstem ->
                if(grabberSytstem.isExtended()) {
                    grabberSytstem.closed()
                }else{
                    grabberSytstem.extend()
                }
                S4WithdrawnOpen
            },
            gotoExtendedOpen = { grabberSystem ->
                grabberSystem.extend()
                S4WithdrawnOpen
            },
            gotoWithdrawnClosed = { grabberSytstem ->
                grabberSytstem.closed()
                S4WithdrawnOpen
            },
            gotoWithdrawnOpen = { grabberSytstem ->
                S4WithdrawnOpen
            }
    )
}