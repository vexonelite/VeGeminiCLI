
i provide the domain text in ``./domain_context/base_delegates.kt``.




* Request1 (QueryModel for ControlGroup)
```
{
  "Q_CloneId": "string",
  "Q_BaseID": "string",
  "Q_ControlGroupId": "string",
  "Q_Kind": "string",
  "Q_KindName": "string",
  "Q_Name": "string",
  "Q_ReName": "string",
  "Q_Enabled": "string",
  "Q_EnabledName": "string",
  "Q_Keyword": "string",
  "Q_QueryLevel": "string",
  "Q_PageIndex": 0,
  "Q_PageSize": 0,
  "Sort": [
    {
      "ColumnName": "string",
      "Direction": "string"
    }
  ]
}
```


* Response1 (ControlGroup)
```
{
  "Data": [
    {
      "CVCG_CloneId": "string",
      "CVCG_ActInspector": "string",
      "CVCG_ActRecorder": "string",
      "CVCG_BaseId": "string",
      "CVCG_ControlGroupId": "string",
      "CVCG_Enabled": "string",
      "CVCG_EnabledName_XX": "string",
      "CVCG_Kind": "string",
      "CVCG_KindName_XX": "string",
      "CVCG_Name": "string",
      "CVCG_ReName": "string",
    }
  ],
  "Success": true,
  "Code": "string",
  "Message": "string",
  "DataTime": "2026-04-05T01:29:03.367Z",
  "TotalCount": 0
}
```


* Response2 (ControlGroupDetail)
```
{
  "Data": [
    {
      "CVCGD_CloneId": "string",
      "CVCGD_BaseId_XX": "string",
      "CVCGD_CGCloneId": "string",
      "CVCGD_CGID_XX": "string",
      "CVCGD_ControlGroupDetailId": "string",
      "CVCGD_ControlGroupName_XX": "string",
      "CVCGD_Enabled": "string",
      "CVCGD_EnabledName_XX": "string",
      "CVCGD_Name": "string",
      "CVCGD_SortNo": 0,
    }
  ],
  "Success": true,
  "Code": "string",
  "Message": "string",
  "DataTime": "2026-04-05T01:29:03.375Z",
  "TotalCount": 0
}

```


* Response3 (ControlSetting)
```
{
  "Data": [
    {
      "CVCS_CloneId": "string",
      "CVCS_BaseId_XX": "string",
      "CVCS_CGDCloneId": "string",
      "CVCS_CGDID_XX": "string",
      "CVCS_CGDSortNo_XX": 0,
      "CVCS_CGID_XX": "string",
      "CVCS_ControlGroupDetailName_XX": "string",
      "CVCS_ControlGroupName_XX": "string",
      "CVCS_ControlSettingId": "string",
      "CVCS_Name": "string",
      "CVCS_Remark": "string",
      "CVCS_SortNo": 0,
    }
  ],
  "Success": true,
  "Code": "string",
  "Message": "string",
  "DataTime": "2026-04-05T01:29:03.395Z",
  "TotalCount": 0
}
```


* Response4 (ControlProperty)
```
{
  "Data": [
    {
      "CVCP_CloneId": "string",
      "CVCP_BaseId_XX": "string",
      "CVCP_CGDID_XX": "string",
      "CVCP_CGDSortNo_XX": 0,
      "CVCP_CGID_XX": "string",
      "CVCP_ControlCode_XX": "string",
      "CVCP_ControlCodeGroupId": "string",
      "CVCP_ControlCodeGroupName_XX": "string",
      "CVCP_ControlCodeGroupSortNo_XX": 0,
      "CVCP_ControlCodeId": "string",
      "CVCP_ControlCodeName_XX": "string",
      "CVCP_ControlCodeSortNo_XX": 0,
      "CVCP_ControlGroupDetailName_XX": "string",
      "CVCP_ControlGroupName_XX": "string",
      "CVCP_ControlPropertyId": "string",
      "CVCP_ControlSettingName_XX": "string",
      "CVCP_CSCloneId": "string",
      "CVCP_CSID_XX": "string",
      "CVCP_CSSortNo_XX": 0,
      "CVCP_SortNo": 0,
    }
  ],
  "Success": true,
  "Code": "string",
  "Message": "string",
  "DataTime": "2026-04-05T02:46:38.060Z",
  "TotalCount": 0
}
```


* Response5 (ControlPropertyItem)
```
{
  "Data": [
    {
      "CVCPI_CloneId": "string",
      "CVCPI_BaseId_XX": "string",
      "CVCPI_CGDID_XX": "string",
      "CVCPI_CGDSortNo_XX": 0,
      "CVCPI_CGID_XX": "string",
      "CVCPI_Code": "string",
      "CVCPI_CodeGroupId": "string",
      "CVCPI_ControlCode_XX": "string",
      "CVCPI_ControlGroupDetailName_XX": "string",
      "CVCPI_ControlGroupName_XX": "string",
      "CVCPI_ControlPropertyId": "string",
      "CVCPI_ControlPropertyItemId": "string",
      "CVCPI_ControlSettingName_XX": "string",
      "CVCPI_CPCloneId": "string",
      "CVCPI_CPID_XX": "string",
      "CVCPI_CPSortNo_XX": 0,
      "CVCPI_CSID_XX": "string",
      "CVCPI_CSSortNo_XX": 0,
      "CVCPI_DIndex": 0,
      "CVCPI_FileMode": "string",
      "CVCPI_Height": 0,
      "CVCPI_Index": 0,
      "CVCPI_ItemLabel": "string",
      "CVCPI_ItemSort": 0,
      "CVCPI_ItemValue": "string",
      "CVCPI_MIndex": 0,
      "CVCPI_NodeId": "string",
      "CVCPI_ParentId": "string",
      "CVCPI_RecordValue": "string",
      "CVCPI_RelactionClickValue": "string",
      "CVCPI_RelactionControl": "string",
      "CVCPI_RelactionControlName": "string",
      "CVCPI_RelactionValue": "string",
      "CVCPI_Rows": 0,
      "CVCPI_Subject": "string",
      "CVCPI_Type": "string",
      "CVCPI_Url": "string",
      "CVCPI_UrlAction": "string",
      "CVCPI_Width": 0
    }
  ],
  "Success": true,
  "Code": "string",
  "Message": "string",
  "DataTime": "2026-04-05T02:46:38.066Z",
  "TotalCount": 0
}
```


* Response6 (ControlCode)
```
{
  "Data": [
    {
      "SYCC_ControlCodeId": "string",
      "SYCC_Code": "string",
      "SYCC_Enabled": "string",
      "SYCC_EnabledName_XX": "string",
      "SYCC_Icon": "string",
      "SYCC_Name": "string",
      "SYCC_SortNo": 0,
    }
  ],
  "Success": true,
  "Code": "string",
  "Message": "string",
  "DataTime": "2026-04-05T02:46:38.486Z",
  "TotalCount": 0
}
```


* Response7 (Nested response for ControlGroup)
```
{
  "Data": [
    {
      "Children_ControlGroupDetail": [
        {
          "Children_ControlSetting": [
            {
              "Children_ControlProperty": [
                {
                  "Children_ControlCode": [
                    {
                      "SYCC_ControlCodeId": "string",
                      "SYCC_Code": "string",
                      "SYCC_Enabled": "string",
                      "SYCC_EnabledName_XX": "string",
                      "SYCC_Icon": "string",
                      "SYCC_Name": "string",
                      "SYCC_SortNo": 0,
                    }
                  ],
                  "Children_ControlCodeGroup": [
                    {
                      "Children_ControlProperty": [
                        {
                          "CVCP_CloneId": "string",
                          "CVCP_BaseId_XX": "string",
                          "CVCP_CGDID_XX": "string",
                          "CVCP_CGDSortNo_XX": 0,
                          "CVCP_CGID_XX": "string",
                          "CVCP_ControlCode_XX": "string",
                          "CVCP_ControlCodeGroupId": "string",
                          "CVCP_ControlCodeGroupName_XX": "string",
                          "CVCP_ControlCodeGroupSortNo_XX": 0,
                          "CVCP_ControlCodeId": "string",
                          "CVCP_ControlCodeName_XX": "string",
                          "CVCP_ControlCodeSortNo_XX": 0,
                          "CVCP_ControlGroupDetailName_XX": "string",
                          "CVCP_ControlGroupName_XX": "string",
                          "CVCP_ControlPropertyId": "string",
                          "CVCP_ControlSettingName_XX": "string",
                          "CVCP_CSCloneId": "string",
                          "CVCP_CSID_XX": "string",
                          "CVCP_CSSortNo_XX": 0,
                          "CVCP_SortNo": 0,
                        }
                      ],
                      "Children_ControlPropertyItem": [
                        {
                          "CVCPI_CloneId": "string",
                          "CVCPI_BaseId_XX": "string",
                          "CVCPI_CGDID_XX": "string",
                          "CVCPI_CGDSortNo_XX": 0,
                          "CVCPI_CGID_XX": "string",
                          "CVCPI_Code": "string",
                          "CVCPI_CodeGroupId": "string",
                          "CVCPI_ControlCode_XX": "string",
                          "CVCPI_ControlGroupDetailName_XX": "string",
                          "CVCPI_ControlGroupName_XX": "string",
                          "CVCPI_ControlPropertyId": "string",
                          "CVCPI_ControlPropertyItemId": "string",
                          "CVCPI_ControlSettingName_XX": "string",
                          "CVCPI_CPCloneId": "string",
                          "CVCPI_CPID_XX": "string",
                          "CVCPI_CPSortNo_XX": 0,
                          "CVCPI_CSID_XX": "string",
                          "CVCPI_CSSortNo_XX": 0,
                          "CVCPI_DIndex": 0,
                          "CVCPI_FileMode": "string",
                          "CVCPI_Height": 0,
                          "CVCPI_Index": 0,
                          "CVCPI_ItemLabel": "string",
                          "CVCPI_ItemSort": 0,
                          "CVCPI_ItemValue": "string",
                          "CVCPI_MIndex": 0,
                          "CVCPI_NodeId": "string",
                          "CVCPI_ParentId": "string",
                          "CVCPI_RecordValue": "string",
                          "CVCPI_RelactionClickValue": "string",
                          "CVCPI_RelactionControl": "string",
                          "CVCPI_RelactionControlName": "string",
                          "CVCPI_RelactionValue": "string",
                          "CVCPI_Rows": 0,
                          "CVCPI_Subject": "string",
                          "CVCPI_Type": "string",
                          "CVCPI_Url": "string",
                          "CVCPI_UrlAction": "string",
                          "CVCPI_Width": 0
                        }
                      ],
                      "CVCCG_CloneId": "string",
                      "CVCCG_ControlCodeGroupId": "string",
                      "CVCCG_CPCloneId": "string",
                      "CVCCG_Enabled": "string",
                      "CVCCG_EnabledName_XX": "string",
                      "CVCCG_Name": "string",
                      "CVCCG_SortNo": 0,
                    }
                  ],
                  "Children_ControlPropertyItem": [
                    {
                      "CVCPI_CloneId": "string",
                      "CVCPI_BaseId_XX": "string",
                      "CVCPI_CGDID_XX": "string",
                      "CVCPI_CGDSortNo_XX": 0,
                      "CVCPI_CGID_XX": "string",
                      "CVCPI_Code": "string",
                      "CVCPI_CodeGroupId": "string",
                      "CVCPI_ControlCode_XX": "string",
                      "CVCPI_ControlGroupDetailName_XX": "string",
                      "CVCPI_ControlGroupName_XX": "string",
                      "CVCPI_ControlPropertyId": "string",
                      "CVCPI_ControlPropertyItemId": "string",
                      "CVCPI_ControlSettingName_XX": "string",
                      "CVCPI_CPCloneId": "string",
                      "CVCPI_CPID_XX": "string",
                      "CVCPI_CPSortNo_XX": 0,
                      "CVCPI_CSID_XX": "string",
                      "CVCPI_CSSortNo_XX": 0,
                      "CVCPI_DIndex": 0,
                      "CVCPI_FileMode": "string",
                      "CVCPI_Height": 0,
                      "CVCPI_Index": 0,
                      "CVCPI_ItemLabel": "string",
                      "CVCPI_ItemSort": 0,
                      "CVCPI_ItemValue": "string",
                      "CVCPI_MIndex": 0,
                      "CVCPI_NodeId": "string",
                      "CVCPI_ParentId": "string",
                      "CVCPI_RecordValue": "string",
                      "CVCPI_RelactionClickValue": "string",
                      "CVCPI_RelactionControl": "string",
                      "CVCPI_RelactionControlName": "string",
                      "CVCPI_RelactionValue": "string",
                      "CVCPI_Rows": 0,
                      "CVCPI_Subject": "string",
                      "CVCPI_Type": "string",
                      "CVCPI_Url": "string",
                      "CVCPI_UrlAction": "string",
                      "CVCPI_Width": 0
                    }
                  ],
                  "CVCP_CloneId": "string",
                  "CVCP_BaseId_XX": "string",
                  "CVCP_CGDID_XX": "string",
                  "CVCP_CGDSortNo_XX": 0,
                  "CVCP_CGID_XX": "string",
                  "CVCP_ControlCode_XX": "string",
                  "CVCP_ControlCodeGroupId": "string",
                  "CVCP_ControlCodeGroupName_XX": "string",
                  "CVCP_ControlCodeGroupSortNo_XX": 0,
                  "CVCP_ControlCodeId": "string",
                  "CVCP_ControlCodeName_XX": "string",
                  "CVCP_ControlCodeSortNo_XX": 0,
                  "CVCP_ControlGroupDetailName_XX": "string",
                  "CVCP_ControlGroupName_XX": "string",
                  "CVCP_ControlPropertyId": "string",
                  "CVCP_ControlSettingName_XX": "string",
                  "CVCP_CSCloneId": "string",
                  "CVCP_CSID_XX": "string",
                  "CVCP_CSSortNo_XX": 0,
                  "CVCP_SortNo": 0,
                }
              ],
              "CVCS_CloneId": "string",
              "CVCS_BaseId_XX": "string",
              "CVCS_CGDCloneId": "string",
              "CVCS_CGDID_XX": "string",
              "CVCS_CGDSortNo_XX": 0,
              "CVCS_CGID_XX": "string",
              "CVCS_ControlGroupDetailName_XX": "string",
              "CVCS_ControlGroupName_XX": "string",
              "CVCS_ControlSettingId": "string",
              "CVCS_Name": "string",
              "CVCS_Remark": "string",
              "CVCS_SortNo": 0,
            }
          ],
          "CVCGD_CloneId": "string",
          "CVCGD_BaseId_XX": "string",
          "CVCGD_CGCloneId": "string",
          "CVCGD_CGID_XX": "string",
          "CVCGD_ControlGroupDetailId": "string",
          "CVCGD_ControlGroupName_XX": "string",
          "CVCGD_Enabled": "string",
          "CVCGD_EnabledName_XX": "string",
          "CVCGD_Name": "string",
          "CVCGD_SortNo": 0,
        }
      ],
      "CVCG_CloneId": "string",
      "CVCG_ActInspector": "string",
      "CVCG_ActRecorder": "string",
      "CVCG_BaseId": "string",
      "CVCG_ControlGroupId": "string",
      "CVCG_Enabled": "string",
      "CVCG_EnabledName_XX": "string",
      "CVCG_Kind": "string",
      "CVCG_KindName_XX": "string",
      "CVCG_Name": "string",
      "CVCG_ReName": "string",
    }
  ],
  "Success": true,
  "Code": "string",
  "Message": "string",
  "DataTime": "2026-04-05T01:29:03.368Z",
  "TotalCount": 0
}
```

