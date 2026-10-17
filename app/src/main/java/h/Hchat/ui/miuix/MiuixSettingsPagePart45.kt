package h.Hchat.ui.miuix

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ClipData
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color as AndroidColor
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Handler
import android.os.Bundle
import android.os.Looper
import android.os.Parcelable
import android.os.SystemClock
import android.provider.OpenableColumns
import android.provider.MediaStore
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebView
import android.widget.DatePicker
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TimePicker
import android.widget.Toast
import android.widget.VideoView
import h.Hchat.BuildConfig
import h.Hchat.R
import h.Hchat.crash.CrashReportRuntime
import h.Hchat.crash.CrashReportSettings
import h.Hchat.crash.CrashReportSettingsProvider
import h.Hchat.hooks.api.ui.HchatAgentIconDrawable
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutFeature
import h.Hchat.hooks.items.script.market.PluginMarketClient
import h.Hchat.hooks.items.script.market.PluginMarketSettings
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutGlyph
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutGlyphDrawable
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutGlyphs
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutIconPickResult
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutIconPicker
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutIconStore
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutItem
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutRuntime
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutSettings
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.SideEffect
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.NavigationEventDispatcherOwner
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import h.Hchat.hooks.api.core.WeChatApis
import h.Hchat.hooks.api.media.WeChatFavoriteItem
import h.Hchat.hooks.api.message.WeChatMessageObserveApi
import h.Hchat.hooks.api.model.ContactLabelBean
import h.Hchat.hooks.api.model.WeChatContact
import h.Hchat.hooks.items.audiotransform.AudioTransformFeature
import h.Hchat.hooks.items.audiotransform.AudioTransformCancelledException
import h.Hchat.hooks.items.audiotransform.AudioTransformSegmenter
import h.Hchat.hooks.items.audiotransform.AudioTransformSettings
import h.Hchat.hooks.items.antirecall.AntiRecallFeature
import h.Hchat.hooks.items.antirecall.AntiRecallSettings
import h.Hchat.hooks.items.autoreply.AutoReplyFeature
import h.Hchat.hooks.items.autoreply.AutoReplyRule
import h.Hchat.hooks.items.autoreply.AutoReplyRuntime
import h.Hchat.hooks.items.autoreply.AutoReplySettings
import h.Hchat.hooks.items.autoreply.AutoReplyStep
import h.Hchat.hooks.items.autoreply.XiaozhiMcpStatus
import h.Hchat.hooks.items.autoreply.AutoReplyXiaozhiConfig
import h.Hchat.hooks.items.autoreply.AutoReplyZhiliaConfig
import h.Hchat.hooks.items.autoreply.XiaozhiAgentOption
import h.Hchat.hooks.items.autoreply.XiaozhiConsoleApi
import h.Hchat.hooks.items.autoreply.XiaozhiModelOption
import h.Hchat.hooks.items.autoreply.XiaozhiVoiceOption
import h.Hchat.hooks.items.autooriginal.AutoOriginalImageFeature
import h.Hchat.hooks.items.autooriginal.AutoOriginalImageSettings
import h.Hchat.hooks.items.autovieworiginal.AutoViewOriginalFeature
import h.Hchat.hooks.items.autovieworiginal.AutoViewOriginalSettings
import h.Hchat.hooks.items.atallnotify.AtAllNotificationBlockFeature
import h.Hchat.hooks.items.atallnotify.AtAllNotificationBlockSettings
import h.Hchat.hooks.items.automessageforward.AutoMessageForwardFeature
import h.Hchat.hooks.items.automessageforward.AutoMessageForwardRule
import h.Hchat.hooks.items.automessageforward.AutoMessageForwardSettings
import h.Hchat.hooks.items.callmedialimit.CallMediaLimitFeature
import h.Hchat.hooks.items.callmedialimit.CallMediaLimitSettings
import h.Hchat.hooks.items.callmedialimit.CallRingtoneBlockFeature
import h.Hchat.hooks.items.chattime.ChatTimeStyleFeature
import h.Hchat.hooks.items.chattime.ChatTimeStyleSettings
import h.Hchat.hooks.items.conversationgroup.ConversationGroup
import h.Hchat.hooks.items.conversationgroup.ConversationGroupFeature
import h.Hchat.hooks.items.conversationgroup.ConversationGroupPickerSupport
import h.Hchat.hooks.items.conversationgroup.ConversationGroupRuntime
import h.Hchat.hooks.items.conversationgroup.ConversationGroupStore
import h.Hchat.hooks.items.conversationtabs.ConversationTabsFeature
import h.Hchat.hooks.items.conversationtabs.ConversationTabsStore
import h.Hchat.hooks.items.custombottombar.CustomBottomBarFeature
import h.Hchat.hooks.items.custombottombar.FloatingBottomBarSettings
import h.Hchat.hooks.items.customnotify.CustomNotificationFeature
import h.Hchat.hooks.items.customnotify.CustomNotificationRule
import h.Hchat.hooks.items.customnotify.CustomNotificationRuntime
import h.Hchat.hooks.items.customfriendavatar.CustomFriendAvatarFeature
import h.Hchat.hooks.items.customfriendavatar.CustomFriendAvatarPicker
import h.Hchat.hooks.items.customfriendavatar.CustomFriendAvatarSettings
import h.Hchat.hooks.items.customfriendavatar.CustomFriendAvatarStore
import h.Hchat.hooks.items.customnotify.CustomNotificationSettings
import h.Hchat.hooks.items.editmsg.EditMessageFeature
import h.Hchat.hooks.items.editmsg.EditMessageSettings
import h.Hchat.hooks.items.emojisave.EmojiSaveFeature
import h.Hchat.hooks.items.emojisave.EmojiSaveSettings
import h.Hchat.hooks.items.fakelocation.FakeLocationFeature
import h.Hchat.hooks.items.fakelocation.FakeLocationSettings
import h.Hchat.hooks.items.fakescancamera.FakeScanCameraFeature
import h.Hchat.hooks.items.fakescancamera.FakeScanCameraSettings
import h.Hchat.hooks.items.fakevoiceduration.FakeVoiceDurationFeature
import h.Hchat.hooks.items.fakevoiceduration.FakeVoiceDurationSettings
import h.Hchat.hooks.items.forwardlimit.RemoveForwardLimitFeature
import h.Hchat.hooks.items.forwardlimit.RemoveForwardLimitSettings
import h.Hchat.hooks.items.gameemoji.GameEmojiFeature
import h.Hchat.hooks.items.gameemoji.GameEmojiSettings
import h.Hchat.hooks.items.groupleave.GroupLeaveMonitorFeature
import h.Hchat.hooks.items.groupleave.GroupLeaveReplyTemplate
import h.Hchat.hooks.items.groupleave.GroupLeaveReplyTemplateBinding
import h.Hchat.hooks.items.groupleave.GroupLeaveMonitorSettings
import h.Hchat.hooks.items.grouplabel.GroupChatLabel
import h.Hchat.hooks.items.grouplabel.GroupChatLabelFeature
import h.Hchat.hooks.items.grouplabel.GroupChatLabelStore
import h.Hchat.hooks.items.grouplabel.QuickGroupChatLabelFeature
import h.Hchat.hooks.items.grouplabel.QuickGroupChatLabelSettings
import h.Hchat.hooks.items.groupnicknamecolor.GroupNicknameColorSettings
import h.Hchat.hooks.items.groupnicknamecolor.GroupNicknameColorStore
import h.Hchat.hooks.items.grouprename.GroupRenameMonitorFeature
import h.Hchat.hooks.items.grouprename.GroupRenameReplyTemplate
import h.Hchat.hooks.items.grouprename.GroupRenameMonitorSettings
import h.Hchat.hooks.items.grouprename.GroupRenameTemplateBinding
import h.Hchat.hooks.items.hideavatar.HideChatAvatarFeature
import h.Hchat.hooks.items.hideavatar.HideChatAvatarSettings
import h.Hchat.hooks.items.hidemenu.HideChatMenuFeature
import h.Hchat.hooks.items.hidemenu.HideChatMenuSettings
import h.Hchat.hooks.items.hotupdate.DisableHotUpdateFeature
import h.Hchat.hooks.items.hotupdate.DisableHotUpdateSettings
import h.Hchat.hooks.items.hometextcolor.HomeTextColorFeature
import h.Hchat.hooks.items.hometextcolor.HomeTextColorSettings
import h.Hchat.hooks.items.inputhint.InputHintFeature
import h.Hchat.hooks.items.inputhint.InputHintSettings
import h.Hchat.hooks.items.inputhint.InputHintStats
import h.Hchat.hooks.items.inputhint.OutgoingMessageStatsRepository
import h.Hchat.hooks.items.keepalive.WeChatKeepAliveFeature
import h.Hchat.hooks.items.keepalive.WeChatKeepAliveRuntime
import h.Hchat.hooks.items.keepalive.WeChatKeepAliveSettings
import h.Hchat.hooks.items.keywordnotify.KeywordNotificationFeature
import h.Hchat.hooks.items.keywordnotify.KeywordNotificationRuntime
import h.Hchat.hooks.items.keywordnotify.KeywordNotificationSettings
import h.Hchat.hooks.items.keywordnotify.KeywordRule
import h.Hchat.hooks.items.zombiecheck.ZombieCheckController
import h.Hchat.hooks.items.zombiecheck.ZombieCheckActionResult
import h.Hchat.hooks.items.zombiecheck.ZombieCheckFeature
import h.Hchat.hooks.items.zombiecheck.ZombieCheckResultType
import h.Hchat.hooks.items.zombiecheck.ZombieCheckSettings
import h.Hchat.hooks.items.membertitle.MemberTitleFeature
import h.Hchat.hooks.items.membertitle.MemberTitleSettings
import h.Hchat.hooks.items.membertitle.MemberTitleStore
import h.Hchat.hooks.items.messageaffix.MessageAffixFeature
import h.Hchat.hooks.items.messageaffix.MessageAffixSettings
import h.Hchat.hooks.items.messagebubble.MessageBubbleFeature
import h.Hchat.hooks.items.messagebubble.MessageBubblePickResult
import h.Hchat.hooks.items.messagebubble.MessageBubblePicker
import h.Hchat.hooks.items.messagebubble.MessageBubbleSettings
import h.Hchat.hooks.items.messagebubble.MessageBubbleSlot
import h.Hchat.hooks.items.messagebubble.MessageBubbleStore
import h.Hchat.hooks.items.messagebubble.MessageBubbleKind
import h.Hchat.hooks.items.messagetextcolor.MessageTextColorFeature
import h.Hchat.hooks.items.messagetextcolor.MessageTextColorSettings
import h.Hchat.hooks.items.messageblock.MessageBlockBinding
import h.Hchat.hooks.items.messageblock.MessageBlockDefaultRule
import h.Hchat.hooks.items.messageblock.MessageBlockFeature
import h.Hchat.hooks.items.messageblock.MessageBlockSettings
import h.Hchat.hooks.items.messageblock.MessageBlockTemplate
import h.Hchat.hooks.items.messageforward.MessageForwardFeature
import h.Hchat.hooks.items.messageforward.MessageForwardSettings
import h.Hchat.hooks.items.miniprogrambaselib.FakeMiniProgramBaseLibFeature
import h.Hchat.hooks.items.miniprogrambaselib.FakeMiniProgramBaseLibSettings
import h.Hchat.hooks.items.miniprogramsplashad.SkipGlobalMiniProgramSplashAdsFeature
import h.Hchat.hooks.items.miniprogramsplashad.SkipGlobalMiniProgramSplashAdsSettings
import h.Hchat.hooks.items.miniprogramvideoad.SkipMiniProgramVideoAdsFeature
import h.Hchat.hooks.items.miniprogramvideoad.SkipMiniProgramVideoAdsSettings
import h.Hchat.hooks.items.musicorder.QQMusicOrderFeature
import h.Hchat.hooks.items.musicorder.QQMusicOrderSettings
import h.Hchat.hooks.items.moments.OriginalMomentsUploadFeature
import h.Hchat.hooks.items.moments.OriginalMomentsUploadSettings
import h.Hchat.hooks.items.moments.MomentsAutoCommentFeature
import h.Hchat.hooks.items.moments.MomentsAutoCommentSettings
import h.Hchat.hooks.items.moments.MomentsAutoLikeFeature
import h.Hchat.hooks.items.moments.MomentsAutoLikeSettings
import h.Hchat.hooks.items.moments.MomentsAutoForwardFeature
import h.Hchat.hooks.items.moments.MomentsAutoForwardSettings
import h.Hchat.hooks.items.moments.MomentsAutoRefreshFeature
import h.Hchat.hooks.items.moments.MomentsAutoRefreshSettings
import h.Hchat.hooks.items.moments.MomentsBottomDetailFeature
import h.Hchat.hooks.items.moments.MomentsBottomDetailSettings
import h.Hchat.hooks.items.moments.MomentsContactFilterFeature
import h.Hchat.hooks.items.moments.MomentsContactFilterSettings
import h.Hchat.hooks.items.moments.MomentsKeywordBlockFeature
import h.Hchat.hooks.items.moments.MomentsKeywordBlockSettings
import h.Hchat.hooks.items.moments.MomentsPostNotificationFeature
import h.Hchat.hooks.items.moments.MomentsPostNotificationSettings
import h.Hchat.hooks.items.moments.MomentsUploadTailFeature
import h.Hchat.hooks.items.moments.MomentsUploadTailSettings
import h.Hchat.hooks.items.moments.RemoveMomentsAdsFeature
import h.Hchat.hooks.items.moments.RemoveMomentsAdsSettings
import h.Hchat.hooks.items.moments.SnsAntiRecallFeature
import h.Hchat.hooks.items.moments.SnsAntiRecallSettings
import h.Hchat.hooks.items.momentsfake.MomentsFakeCommentSettingsProvider
import h.Hchat.hooks.items.momentsfake.MomentsFakeForwardRuntimeRegistry
import h.Hchat.hooks.items.momentsfake.MomentsFakeForwardSettingsProvider
import h.Hchat.hooks.items.momentsfake.MomentsFakeLikeSettingsProvider
import h.Hchat.hooks.items.momentsfake.MomentsFakeLikeCandidateRepository
import h.Hchat.hooks.items.momentsfake.MomentsFakeInteractionRuntimeRegistry
import h.Hchat.hooks.items.momentsfake.MomentsFakeInteractionSettings
import h.Hchat.hooks.items.multirecall.MultiRecallFeature
import h.Hchat.hooks.items.multirecall.MultiRecallSettings
import h.Hchat.hooks.items.payment.core.AutoRedPacketFeature
import h.Hchat.hooks.items.payment.core.PaymentTemplateTimeFormatter
import h.Hchat.hooks.items.payment.core.RedPacketReplyStep
import h.Hchat.hooks.items.payment.core.RedPacketRuleBinding
import h.Hchat.hooks.items.payment.core.RedPacketRuleConfig
import h.Hchat.hooks.items.payment.core.RedPacketRuleTemplate
import h.Hchat.hooks.items.payment.core.RedPacketSettings
import h.Hchat.hooks.items.payment.fakebalance.FakeWalletBalanceFeature
import h.Hchat.hooks.items.payment.fakebalance.FakeWalletBalanceSettings
import h.Hchat.hooks.items.payment.gift.AutoGiftFeature
import h.Hchat.hooks.items.payment.gift.GiftSettings
import h.Hchat.hooks.items.payment.transfer.AutoTransferFeature
import h.Hchat.hooks.items.payment.transfer.AutoTransferSettings
import h.Hchat.hooks.items.payment.transfer.TransferReceiveAccountStore
import h.Hchat.hooks.items.payment.transfer.TransferRuleBinding
import h.Hchat.hooks.items.payment.transfer.TransferRuleConfig
import h.Hchat.hooks.items.payment.transfer.TransferRuleTemplate
import h.Hchat.hooks.items.patblock.PatBlockFeature
import h.Hchat.hooks.items.patblock.PatBlockSettings
import h.Hchat.hooks.items.profileid.ProfileIdFeature
import h.Hchat.hooks.items.profileid.ProfileIdSettings
import h.Hchat.hooks.items.protobuf.ProtobufPacketFeature
import h.Hchat.hooks.items.protobuf.ProtobufPacketRuntime
import h.Hchat.hooks.items.protobuf.ProtobufPacketSettings
import h.Hchat.hooks.items.quoteclear.QuoteDeleteClearFeature
import h.Hchat.hooks.items.quoteclear.QuoteDeleteClearSettings
import h.Hchat.hooks.items.quickcontactedit.QuickContactEditFeature
import h.Hchat.hooks.items.quickcontactedit.QuickContactEditSettings
import h.Hchat.hooks.items.quickmoments.QuickMomentsFeature
import h.Hchat.hooks.items.quickmoments.QuickMomentsSettings
import h.Hchat.hooks.items.quickread.QuickMarkReadFeature
import h.Hchat.hooks.items.quickread.QuickMarkReadSettings
import h.Hchat.hooks.items.quickterminate.QuickTerminateFeature
import h.Hchat.hooks.items.quickterminate.QuickTerminateSettings
import h.Hchat.hooks.items.realtail.RealNameTailFeature
import h.Hchat.hooks.items.realtail.RealNameTailSettings
import h.Hchat.hooks.items.realtail.RealNameTailStore
import h.Hchat.hooks.items.roundavatar.RoundAvatarFeature
import h.Hchat.hooks.items.roundavatar.RoundAvatarSettings
import h.Hchat.hooks.items.script.ScriptPluginFeature
import h.Hchat.hooks.items.script.ScriptPluginManager
import h.Hchat.hooks.items.script.ScriptPluginRuntime
import h.Hchat.hooks.items.script.ScriptPluginSettings
import h.Hchat.hooks.items.script.market.PluginMarketHistoryVersion
import h.Hchat.hooks.items.script.market.PluginMarketInstaller
import h.Hchat.hooks.items.script.market.PluginMarketException
import h.Hchat.hooks.items.script.market.PluginMarketFile
import h.Hchat.hooks.items.script.market.PluginMarketComment
import h.Hchat.hooks.items.script.market.PluginMarketNotification
import h.Hchat.hooks.items.script.market.PluginMarketPlugin
import h.Hchat.hooks.items.script.market.PluginMarketRepository
import h.Hchat.hooks.items.script.market.PluginMarketReviewStatus
import h.Hchat.hooks.items.script.market.PluginMarketUserIdentity
import h.Hchat.hooks.items.script.agent.CodexSettings
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentClient
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentAttachment
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentChatMessage
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentConfig
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentCancellation
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentContext
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentDiff
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentDraft
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentEventIds
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentLocalFiles
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentMcpServer
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentReasoning
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentRequest
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentResumeState
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentSession
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentSessionStore
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentSettings
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentProfile
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentQuotedMessage
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentSpeech
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentTextMerge
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentToolEvent
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentToolResultStore
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentValidator
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWorkspaceChange
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWorkspaceToolConfirmation
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWorkspaceWriteDecision
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWorkspaceTools
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWriter
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskContentItem
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskFeature
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskItem
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskRuntimeCoordinator
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskSettings
import h.Hchat.hooks.items.selectedmessages.SelectedMessageSnapshot
import h.Hchat.hooks.items.selectedmessages.SelectedMessagesFeature
import h.Hchat.hooks.items.selectedmessages.SelectedMessagesRuntimeCoordinator
import h.Hchat.hooks.items.selectedmessages.SelectedMessagesSettings
import h.Hchat.hooks.items.settings.PluginAgentEntryProvider
import h.Hchat.hooks.items.settings.SettingsEntrySettings
import h.Hchat.hooks.items.settings.SettingsFeature
import h.Hchat.hooks.items.shortvideo.FinderMediaDownloadFeature
import h.Hchat.hooks.items.shortvideo.FinderMediaDownloadSettings
import h.Hchat.hooks.items.statuslimit.StatusTextLimitFeature
import h.Hchat.hooks.items.statuslimit.StatusTextLimitSettings
import h.Hchat.hooks.items.swipequote.SwipeQuoteFeature
import h.Hchat.hooks.items.swipequote.SwipeQuoteSettings
import h.Hchat.hooks.items.tablet.WeChatTabletFeature
import h.Hchat.hooks.items.tablet.WeChatTabletSettings
import h.Hchat.hooks.items.textspeech.TextSpeechFeature
import h.Hchat.hooks.items.textspeech.TextSpeechEngineCatalog
import h.Hchat.hooks.items.textspeech.TextSpeechSettings
import h.Hchat.hooks.items.textspeech.TextSpeechVoiceCatalog
import h.Hchat.hooks.items.textvoice.TextVoiceFeature
import h.Hchat.hooks.items.textvoice.TextVoiceSettings
import h.Hchat.hooks.items.typingreport.TypingReportBlockFeature
import h.Hchat.hooks.items.typingreport.TypingReportBlockSettings
import h.Hchat.hooks.items.voiceforward.VoiceForwardFeature
import h.Hchat.hooks.items.voiceforward.VoiceForwardSettings
import h.Hchat.hooks.items.hchatextra.GroupMemberHistorySettingsProvider
import h.Hchat.hooks.items.hchatextra.MessageDetailsSettingsProvider
import h.Hchat.hooks.items.hchatextra.RedPacketDetailsSettingsProvider
import h.Hchat.hooks.items.hchatextra.SkipWebRiskSettingsProvider
import h.Hchat.hooks.items.hchatextra.HchatExtraSettings
import h.Hchat.hooks.items.voicepreview.VoicePreviewFeature
import h.Hchat.hooks.items.voicepreview.VoicePreviewSettings
import h.Hchat.media.AudioTransformBridge
import h.Hchat.preferences.HchatStorage
import h.Hchat.preferences.TermsGate
import h.Hchat.ui.FeatureSettingsProvider
import h.Hchat.ui.UIRegistry
import h.Hchat.utils.KavaReflector
import h.Hchat.utils.KeywordReplacementRule
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import org.json.JSONArray
import org.json.JSONObject
import top.yukonga.miuix.kmp.basic.BreadcrumbBar
import top.yukonga.miuix.kmp.basic.BreadcrumbItem
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.Checkbox
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.PullToRefresh
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.Slider
import top.yukonga.miuix.kmp.basic.TabRow
import top.yukonga.miuix.kmp.basic.DropdownEntry
import top.yukonga.miuix.kmp.basic.DropdownItem
import top.yukonga.miuix.kmp.menu.WindowDropdownMenu
import top.yukonga.miuix.kmp.basic.CircularProgressIndicator
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.Switch
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.nav.core.NavDisplay
import top.yukonga.miuix.kmp.nav.core.NavKey
import top.yukonga.miuix.kmp.nav.core.navBackStackOf
import top.yukonga.miuix.kmp.nav.transition.NavTransitions
import top.yukonga.miuix.kmp.utils.springAnimateToPage
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.blur.Backdrop
import top.yukonga.miuix.kmp.blur.layerBackdrop
import top.yukonga.miuix.kmp.blur.rememberLayerBackdrop
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.theme.darkColorScheme
import top.yukonga.miuix.kmp.theme.lightColorScheme
import top.yukonga.miuix.kmp.window.WindowDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.io.File
import java.lang.ref.WeakReference
import java.net.URL
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Calendar
import java.util.LinkedHashSet
import java.util.Locale
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.abs
import kotlin.random.Random

@Composable
internal fun FavoriteMediaThumbnail(
    item: WeChatFavoriteItem,
    onPreview: (String) -> Unit
) {
    var previewPath by remember(item.localId) { mutableStateOf("") }
    var thumbnail by remember(item.localId) { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(item.localId, item.type) {
        if (item.type != 2 && item.type != 4) return@LaunchedEffect
        val result = withContext(Dispatchers.IO) {
            val path = WeChatApis.media()?.favorites()?.previewPath(item.localId).orEmpty()
            path to path.takeIf { it.isNotBlank() }
                ?.let { loadFavoriteThumbnail(it, item.type, 320) }
        }
        previewPath = result.first
        thumbnail = result.second
    }
    val previewModifier = if (previewPath.isNotBlank()) {
        Modifier.responsiveTap(onClick = { onPreview(previewPath) })
    } else {
        Modifier
    }
    Box(
        modifier = Modifier.size(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MiuixTheme.colorScheme.secondaryVariant)
            .then(previewModifier),
        contentAlignment = Alignment.Center
    ) {
        val image = thumbnail
        if (image != null) {
            Image(
                bitmap = image,
                contentDescription = if (item.type == 4) "预览视频" else "预览图片",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if (item.type == 4) {
                Box(
                    modifier = Modifier.size(28.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.Black.copy(alpha = 0.56f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = NavIcons.Entertainment,
                        contentDescription = "播放",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        } else {
            Text(
                text = item.typeLabel().take(1).ifEmpty { "藏" },
                color = MiuixTheme.colorScheme.onSecondaryVariant,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

internal fun loadFavoriteThumbnail(path: String, type: Int, maxDimension: Int): ImageBitmap? {
    if (!File(path).isFile) return null
    return if (type == 4) {
        @Suppress("DEPRECATION")
        ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND)?.asImageBitmap()
    } else {
        decodeFavoriteImage(path, maxDimension)
    }
}

internal fun decodeFavoriteImage(path: String, maxDimension: Int): ImageBitmap? {
    val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    BitmapFactory.decodeFile(path, bounds)
    if (bounds.outWidth <= 0 || bounds.outHeight <= 0) return null
    var sample = 1
    while (bounds.outWidth / sample > maxDimension * 2 || bounds.outHeight / sample > maxDimension * 2) {
        sample *= 2
    }
    return BitmapFactory.decodeFile(path, BitmapFactory.Options().apply { inSampleSize = sample })
        ?.asImageBitmap()
}

@Composable
internal fun FavoriteMediaPreviewDialog(
    media: FavoriteMediaPreview,
    onClose: () -> Unit
) {
    var image by remember(media.path) { mutableStateOf<ImageBitmap?>(null) }
    var videoView by remember(media.path) { mutableStateOf<VideoView?>(null) }
    val previewHeight = (LocalConfiguration.current.screenHeightDp.dp * 0.62f).coerceAtMost(560.dp)
    LaunchedEffect(media.path, media.item.type) {
        if (media.item.type == 2) {
            image = withContext(Dispatchers.IO) { decodeFavoriteImage(media.path, 2048) }
        }
    }
    DisposableEffect(media.path) {
        onDispose { videoView?.stopPlayback() }
    }
    WindowDialog(
        show = true,
        title = "${media.item.typeLabel()}预览",
        onDismissRequest = onClose,
        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (media.item.type == 4) {
                    AndroidView(
                        factory = { context ->
                            VideoView(context).also { view ->
                                val controller = MediaController(context)
                                controller.setAnchorView(view)
                                view.setMediaController(controller)
                                view.setVideoPath(media.path)
                                view.setOnPreparedListener { view.start() }
                                videoView = view
                            }
                        },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 240.dp, max = previewHeight)
                    )
                } else {
                    val bitmap = image
                    if (bitmap == null) {
                        EmptyText("正在载入预览...")
                    } else {
                        Image(
                            bitmap = bitmap,
                            contentDescription = "图片预览",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxWidth().heightIn(min = 220.dp, max = previewHeight)
                        )
                    }
                }
                TextButton(
                    text = "关闭",
                    onClick = onClose,
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    colors = ButtonDefaults.textButtonColorsPrimary()
                )
            }
        }
    )
}

@Composable
internal fun ContactRow(
    option: ContactOption,
    selected: Boolean,
    multiSelect: Boolean,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val pressFeedbackColor = rememberPressFeedbackColor(pressed)
    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(pressFeedbackColor)
            .responsiveTap(
                onClick = onClick,
                onPressedChange = { pressed = it }
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContactAvatar(option)
        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
            Text(text = option.label, color = MiuixTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
            val summary = buildList {
                add(option.id)
                option.labels.takeIf { it.isNotEmpty() }?.let { add(it.joinToString(" / ")) }
                option.extraSummary.takeIf { it.isNotBlank() }?.let(::add)
            }.joinToString(" · ")
            Text(text = summary, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp)
        }
        SelectionMark(selected = selected, multiSelect = multiSelect)
    }
}

@Composable
internal fun ContactAvatar(option: ContactOption) {
    val context = LocalContext.current
    var bitmap by remember(option.id, option.avatarUrl, option.avatarBackupUrl) { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(option.id, option.avatarUrl, option.avatarBackupUrl) {
        bitmap = loadAvatarBitmap(option.id, option.avatarUrl, option.avatarBackupUrl)
    }
    val image = bitmap
    val avatarBg = if (image == null && option.group) Color(0xFF2E7DFF) else MiuixTheme.colorScheme.secondaryVariant
    val avatarTextColor = if (image == null && option.group) Color.White else MiuixTheme.colorScheme.onSecondaryVariant
    val avatarShape = if (RoundAvatarSettings.enabled(context)) {
        RoundedCornerShape((42f * RoundAvatarSettings.radiusFactor(context)).dp)
    } else {
        RoundedCornerShape(12.dp)
    }
    Box(
        modifier = Modifier.size(42.dp)
            .clip(avatarShape)
            .background(avatarBg),
        contentAlignment = Alignment.Center
    ) {
        if (image != null) {
            Image(
                bitmap = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Text(
                text = if (option.group) "群" else option.label.take(1).ifEmpty { "友" },
                color = avatarTextColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
internal fun OptionChoiceRow(
    item: OptionItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val pressFeedbackColor = rememberPressFeedbackColor(pressed)
    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(pressFeedbackColor)
            .responsiveTap(
                onClick = onClick,
                onPressedChange = { pressed = it }
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.label, color = MiuixTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
            if (item.summary.isNotEmpty()) {
                Text(text = item.summary, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp)
            }
        }
        SelectionMark(selected = selected, multiSelect = false)
    }
}

@Composable
internal fun SelectionMark(selected: Boolean, multiSelect: Boolean) {
    Box(
        modifier = Modifier.size(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Checkbox(
            modifier = Modifier.size(22.dp),
            state = if (selected) ToggleableState.On else ToggleableState.Off,
            onClick = null
        )
    }
}

@Composable
internal fun BottomActionBar(
    primaryText: String,
    onPrimaryClick: () -> Unit,
    secondaryText: String? = null,
    onSecondaryClick: (() -> Unit)? = null,
    middleText: String? = null,
    onMiddleClick: (() -> Unit)? = null
) {
    val backAction = when {
        secondaryText == "取消" && onSecondaryClick != null -> onSecondaryClick
        middleText == "取消" && onMiddleClick != null -> onMiddleClick
        primaryText == "取消" -> onPrimaryClick
        secondaryText == "返回" && onSecondaryClick != null -> onSecondaryClick
        middleText == "返回" && onMiddleClick != null -> onMiddleClick
        primaryText == "返回" -> onPrimaryClick
        secondaryText == "关闭" && onSecondaryClick != null -> onSecondaryClick
        middleText == "关闭" && onMiddleClick != null -> onMiddleClick
        primaryText == "关闭" -> onPrimaryClick
        else -> null
    }
    RegisterSettingsBackHandler(backAction)
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(MiuixTheme.colorScheme.background.copy(alpha = 0.92f))
            .padding(start = 12.dp, top = 10.dp, end = 12.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (secondaryText != null && onSecondaryClick != null) {
            BottomBarButton(
                text = secondaryText,
                modifier = Modifier.weight(1f),
                filled = false,
                onClick = onSecondaryClick
            )
        }
        if (middleText != null && onMiddleClick != null) {
            BottomBarButton(
                text = middleText,
                modifier = Modifier.weight(1f),
                filled = false,
                onClick = onMiddleClick
            )
        }
        BottomBarButton(
            text = primaryText,
            modifier = Modifier.weight(1f),
            filled = true,
            onClick = onPrimaryClick
        )
    }
}

@Composable
internal fun navigationButtonBottomInset(): Dp {
    val context = LocalContext.current
    val density = LocalDensity.current
    val gestureNavigation = remember(context) { isGestureNavigationMode(context) }
    if (gestureNavigation) return 0.dp
    val navigationBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val resourceBottom = remember(context, density) {
        with(density) { navigationBarHeightPx(context).toDp() }
    }
    val bottomInset = if (navigationBottom > resourceBottom) navigationBottom else resourceBottom
    return if (bottomInset >= NAVIGATION_BUTTON_MIN_INSET) bottomInset else 0.dp
}

internal fun navigationBarHeightPx(context: Context): Int {
    val resources = context.resources
    val id = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (id > 0) {
        runCatching { resources.getDimensionPixelSize(id) }.getOrDefault(0)
    } else {
        0
    }
}

internal fun isGestureNavigationMode(context: Context): Boolean {
    val resolver = context.contentResolver
    val androidMode = runCatching {
        Settings.Secure.getInt(resolver, "navigation_mode", -1)
    }.getOrDefault(-1)
    if (androidMode == 2) return true
    val miuiGlobalGesture = runCatching {
        Settings.Global.getInt(resolver, "force_fsg_nav_bar", 0)
    }.getOrDefault(0)
    val miuiSecureGesture = runCatching {
        Settings.Secure.getInt(resolver, "force_fsg_nav_bar", 0)
    }.getOrDefault(0)
    return miuiGlobalGesture == 1 || miuiSecureGesture == 1
}

@Composable
internal fun BottomBarButton(text: String, modifier: Modifier, filled: Boolean, onClick: () -> Unit) {
    val bg = if (filled) MiuixTheme.colorScheme.primary else MiuixTheme.colorScheme.secondaryVariant
    val fg = if (filled) Color.White else MiuixTheme.colorScheme.onSecondaryVariant
    Text(
        modifier = modifier.clip(RoundedCornerShape(14.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(vertical = 13.dp),
        text = text,
        color = fg,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center
    )
}

@Composable
internal fun StatsCard(sp: SharedPreferences) {
    SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "红包记录")
    SettingsCard {
        InfoRow("已抢红包", "${sp.getInt(RedPacketSettings.KEY_STATS_COUNT, 0)} 个")
        InsetDivider()
        InfoRow("累计金额", String.format("%.2f 元", sp.getInt(RedPacketSettings.KEY_STATS_AMOUNT, 0) / 100.0))
        InsetDivider()
        InfoRow("今日抢到", "${sp.getInt(RedPacketSettings.KEY_STATS_TODAY, 0)} 个")
        InsetDivider()
        InfoRow("失败次数", "${sp.getInt(RedPacketSettings.KEY_STATS_FAILED, 0)} 次")
    }
}

internal const val MODULE_UPDATE_PREFS = "hchat_module_updates"
internal const val MODULE_UPDATE_IGNORED_CODE = "ignored_version_code"

@Composable
internal fun AboutCard(context: Context) {
    val hostVersion = WeChatApis.version()?.current()?.displayVersion()?.takeIf { it.isNotBlank() } ?: "未知"
    val moduleVersion = BuildConfig.VERSION_NAME.takeIf { it.isNotBlank() } ?: "未知"
    val activity = context as? Activity
    val scope = rememberCoroutineScope()
    var updateInfo by remember { mutableStateOf<ModuleUpdateInfo?>(null) }
    var showUpdateDialog by remember { mutableStateOf(false) }
    var updateError by remember { mutableStateOf("") }
    var historyItems by remember { mutableStateOf<List<ModuleVersionHistory>>(emptyList()) }
    var showHistoryDialog by remember { mutableStateOf(false) }
    var historyError by remember { mutableStateOf("") }
    val updatePrefs = remember { HchatStorage.preferences(context, MODULE_UPDATE_PREFS) }
    val checkForUpdate: () -> Unit = {
        updateError = "正在检查更新…"
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching {
                    ModuleUpdateInfo.fromJson(
                        PluginMarketClient.moduleUpdate(context).getOrThrow(),
                        PluginMarketSettings.serviceUrl(context)
                    )
                }
            }
            result.onSuccess { info ->
                updateInfo = info
                val hasNewerVersion = info.versionCode > BuildConfig.VERSION_CODE
                updatePrefs.edit().putBoolean("has_update", hasNewerVersion).apply()
                if (hasNewerVersion) {
                    updateError = ""
                    showUpdateDialog = true
                } else {
                    updateError = "已是最新版本"
                }
            }.onFailure {
                updateError = "检查更新失败：${it.message ?: "网络不可用"}"
            }
        }
    }
    val openHistory: () -> Unit = {
        historyError = "正在加载历史版本…"
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                PluginMarketClient.moduleVersionHistory(context).map { values ->
                    values.map { ModuleVersionHistory.fromJson(it, PluginMarketSettings.serviceUrl(context)) }
                }
            }
            result.onSuccess {
                historyItems = it
                historyError = if (it.isEmpty()) "暂无历史版本" else ""
                showHistoryDialog = true
            }.onFailure {
                historyError = "加载失败：${it.message ?: "网络不可用"}"
                showHistoryDialog = true
            }
        }
    }
    SettingsCard {
        InfoRow(label = "版本", value = moduleVersion, onClick = checkForUpdate)
        InsetDivider()
        InfoRow(label = "宿主", value = hostVersion)
        InsetDivider()
        InfoRow(label = "作者", value = "。。")
        InsetDivider()
        InfoRow(label = "历史版本", value = "查看更新说明和下载", onClick = openHistory)
        if (updateError.isNotBlank()) {
            Text(updateError, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp))
        }
        updateInfo?.takeIf { it.versionCode > BuildConfig.VERSION_CODE }?.let { update ->
            InsetDivider()
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Text("发现新版本", color = MiuixTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
                Text("${update.versionName}（${update.versionCode}）", color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp, modifier = Modifier.padding(top = 3.dp))
            }
            if (update.releaseNotes.isNotBlank()) {
                Text(update.releaseNotes, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp, modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 10.dp))
            }
            ActionRow("下载新版本", "打开系统下载") {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(update.downloadUrl))
                activity?.startActivity(intent)
            }
        }
    }
    if (showHistoryDialog) {
        WindowDialog(
            show = true,
            title = "历史版本",
            onDismissRequest = { showHistoryDialog = false },
            content = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    if (historyError.isNotBlank()) {
                        Text(historyError, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 14.sp)
                    }
                    historyItems.forEachIndexed { index, version ->
                        if (index > 0) InsetDivider(start = 0.dp)
                        Text("${version.versionName}（${version.versionCode}）", color = MiuixTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 12.dp))
                        Text(version.releaseNotes.ifBlank { "暂无更新说明" }, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp))
                        Text(formatFileSize(version.size), color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                        TextButton("下载此版本", onClick = {
                            activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(version.downloadUrl)))
                        }, colors = ButtonDefaults.textButtonColorsPrimary())
                    }
                }
            }
        )
    }
    if (showUpdateDialog) {
        updateInfo?.takeIf { it.versionCode > BuildConfig.VERSION_CODE }?.let { update ->
            WindowDialog(
                show = true,
                title = "发现新版本 ${update.versionName}",
                onDismissRequest = { showUpdateDialog = false },
                content = {
                    Column {
                        Text(update.releaseNotes.ifBlank { "有新的模块版本可用" }, color = MiuixTheme.colorScheme.onSurface, fontSize = 14.sp)
                        Row(Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TextButton("忽略此版本", onClick = {
                                updatePrefs.edit().putInt(MODULE_UPDATE_IGNORED_CODE, update.versionCode).putBoolean("has_update", false).apply()
                                showUpdateDialog = false
                            }, modifier = Modifier.weight(1f), colors = ButtonDefaults.textButtonColorsPrimary())
                            TextButton("下载更新", onClick = {
                                activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(update.downloadUrl)))
                                showUpdateDialog = false
                            }, modifier = Modifier.weight(1f), colors = ButtonDefaults.textButtonColorsPrimary())
                        }
                    }
                }
            )
        }
    }
}
@Composable
internal fun ModuleUpdateDialog(
    context: Context,
    activity: Activity?,
    update: ModuleUpdateInfo,
    prefs: SharedPreferences,
    onDismiss: () -> Unit
) {
    WindowDialog(
        show = true,
        title = "发现新版本 ${update.versionName}",
        onDismissRequest = onDismiss,
        content = {
            Column {
                Text(update.releaseNotes.ifBlank { "有新的模块版本可用" }, color = MiuixTheme.colorScheme.onSurface, fontSize = 14.sp)
                Row(Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton("忽略此版本", onClick = {
                        prefs.edit().putInt(MODULE_UPDATE_IGNORED_CODE, update.versionCode).putBoolean("has_update", false).apply()
                        onDismiss()
                    }, modifier = Modifier.weight(1f), colors = ButtonDefaults.textButtonColorsPrimary())
                    TextButton("下载更新", onClick = {
                        activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(update.downloadUrl)))
                        onDismiss()
                    }, modifier = Modifier.weight(1f), colors = ButtonDefaults.textButtonColorsPrimary())
                }
            }
        }
    )
}

internal data class ModuleVersionHistory(
    val versionName: String,
    val versionCode: Int,
    val releaseNotes: String,
    val size: Long,
    val downloadUrl: String
) {
    companion object {
        fun fromJson(value: JSONObject, baseUrl: String): ModuleVersionHistory = ModuleVersionHistory(
            value.optString("versionName"),
            value.optInt("versionCode"),
            value.optString("releaseNotes"),
            value.optLong("size"),
            value.optString("apkUrl").let { if (it.startsWith("http")) it else baseUrl.trimEnd('/') + it }
        )
    }
}

internal fun formatFileSize(size: Long): String = when {
    size >= 1024 * 1024 -> String.format("%.1f MiB", size / (1024.0 * 1024.0))
    size >= 1024 -> String.format("%.1f KiB", size / 1024.0)
    else -> "$size B"
}

internal data class ModuleUpdateInfo(val versionName: String, val versionCode: Int, val releaseNotes: String, val downloadUrl: String) {
    companion object {
        fun fromJson(value: JSONObject, baseUrl: String): ModuleUpdateInfo = ModuleUpdateInfo(
            value.optString("versionName"), value.optInt("versionCode"), value.optString("releaseNotes"),
            value.optString("apkUrl").let { if (it.startsWith("http")) it else baseUrl.trimEnd('/') + it }
        )
    }
}


@Composable
internal fun ConfigBackupCard(
    context: Context,
    onImported: () -> Unit
) {
    val activity = context as? Activity
    SettingsCard {
        ActionRow(
            title = "导出配置",
            summary = "",
            onClick = {
                if (activity == null) {
                    Toast.makeText(context, "当前页面无法打开文件管理器", Toast.LENGTH_SHORT).show()
                } else {
                    ConfigImportExportBridge.launchExport(activity)
                }
            }
        )
        InsetDivider()
        ActionRow(
            title = "导入配置",
            summary = "",
            onClick = {
                if (activity == null) {
                    Toast.makeText(context, "当前页面无法打开文件管理器", Toast.LENGTH_SHORT).show()
                } else {
                    ConfigImportExportBridge.launchImport(activity, onImported)
                }
            }
        )
    }
}

@Composable
internal fun GroupLinksCard(context: Context) {
    SettingsCard {
        InfoRow(label = "Telegram Channel", value = "Hchat_ci", onClick = {
            openExternalLink(context, "https://t.me/Hchat_ci")
        })
        InsetDivider()
        InfoRow(label = "Telegram Group", value = "Hchat_Group", onClick = {
            openExternalLink(context, "https://t.me/Hchat_Group")
        })
    }
}

@Composable
internal fun ThanksCard(context: Context) {
    SettingsCard {
        InfoRow(label = "KavaRef", value = "HighCapable", onClick = {
            openExternalLink(context, "https://github.com/HighCapable/KavaRef")
        })
        InsetDivider()
        InfoRow(label = "DexKit", value = "LuckyPray", onClick = {
            openExternalLink(context, "https://github.com/LuckyPray/DexKit")
        })
        InsetDivider()
        InfoRow(label = "FastKV", value = "BillyWei01", onClick = {
            openExternalLink(context, "https://github.com/BillyWei01/FastKV")
        })
        InsetDivider()
        InfoRow(label = "WeChat Pad", value = "lovejiuwu", onClick = {
            openExternalLink(context, "https://github.com/Xposed-Modules-Repo/top.hookvip.wxtablet")
        })
        InsetDivider()
        InfoRow(label = "LSPosed", value = "LSPosed", onClick = {
            openExternalLink(context, "https://github.com/LSPosed/LSPosed")
        })
        InsetDivider()
        InfoRow(label = "Miuix", value = "YuKongA", onClick = {
            openExternalLink(context, "https://github.com/compose-miuix-ui/miuix")
        })
        InsetDivider()
        InfoRow(label = "BeanShell-Android", value = "CopyLibs", onClick = {
            openExternalLink(context, "https://github.com/CopyLibs/BeanShell-Android")
        })
        InsetDivider()
        InfoRow(label = "Silk Codec", value = "YunJavaPro", onClick = {
            openExternalLink(context, "https://github.com/YunJavaPro/Silk-Codec-Android")
        })
    }
}

@Composable
internal fun InfoRow(label: String, value: String, onClick: (() -> Unit)? = null) {
    val modifier = if (onClick != null) {
        Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 16.dp, vertical = 15.dp)
    } else {
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 15.dp)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = MiuixTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = value, color = MiuixTheme.colorScheme.onSurfaceVariantSummary, textAlign = TextAlign.End)
            if (onClick != null) {
                Box(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .size(width = 16.dp, height = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "›",
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 18.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
internal fun XiaozhiMcpStatusRow(status: XiaozhiMcpStatus) {
    val dotColor = if (status.connected) Color(0xFF20C05C) else Color(0xFF8C8C8C)
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "连接状态",
            color = MiuixTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(dotColor)
            )
            Text(
                text = status.label,
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

internal fun openExternalLink(context: Context, url: String) {
    runCatching {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}

internal fun extractFirstHttpUrl(text: String): String? {
    val matcher = android.util.Patterns.WEB_URL.matcher(text)
    while (matcher.find()) {
        val url = matcher.group().orEmpty()
        if (url.startsWith("http://", ignoreCase = true) || url.startsWith("https://", ignoreCase = true)) {
            return url
        }
    }
    return null
}

@Composable
internal fun EmptyText(text: String) {
    Text(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 28.dp),
        text = text,
        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
        textAlign = TextAlign.Center
    )
}

@Composable
internal fun InsetDivider(start: Dp = 16.dp) {
    Box(
        modifier = Modifier
            .padding(start = start)
            .fillMaxWidth()
            .height(Dp.Hairline)
            .background(MiuixTheme.colorScheme.dividerLine)
    )
}

internal class EmbeddedComposeOwner : LifecycleOwner, SavedStateRegistryOwner, ViewModelStoreOwner,
    NavigationEventDispatcherOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)
    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    private val store = ViewModelStore()
    private val navigationDispatcher = NavigationEventDispatcher()
    private val composeRuntime = lazy(LazyThreadSafetyMode.NONE) {
        EmbeddedComposeRuntime(lifecycleRegistry)
    }
    private var restored = false

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    override val viewModelStore: ViewModelStore
        get() = store

    override val navigationEventDispatcher: NavigationEventDispatcher
        get() = navigationDispatcher

    fun install(view: View) {
        EmbeddedComposeOwnerInstaller.install(view, this, this, this, this)
    }

    fun clear(view: View) {
        EmbeddedComposeOwnerInstaller.clear(view)
    }

    fun installComposition(view: ComposeView) {
        composeRuntime.value.install(view)
    }

    fun attach() {
        if (!restored) {
            savedStateRegistryController.performRestore(Bundle.EMPTY)
            restored = true
        }
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    fun destroy() {
        if (lifecycleRegistry.currentState != Lifecycle.State.DESTROYED) {
            lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        }
        if (composeRuntime.isInitialized()) composeRuntime.value.destroy()
        navigationDispatcher.dispose()
        store.clear()
    }
}

internal fun firstTemplate(value: String): String {
    return value.split('|').firstOrNull { it.trim().isNotEmpty() }?.trim() ?: value.trim()
}

internal fun normalizeAntiRecallNoticeText(value: String): String {
    return if (value == AntiRecallSettings.LEGACY_NOTICE_TEXT) {
        AntiRecallSettings.DEFAULT_NOTICE_TEXT
    } else {
        value
    }
}

internal enum class ContactPickerMode {
    FRIENDS,
    GROUPS,
    BOTH,
    ALL_CHATS
}

internal fun ContactPickerMode.supportsGroups(): Boolean {
    return this == ContactPickerMode.GROUPS ||
        this == ContactPickerMode.BOTH ||
        this == ContactPickerMode.ALL_CHATS
}

internal enum class ContactPickerFilter(
    val label: String,
    val title: String
) {
    FRIENDS("好友", "好友"),
    GROUPS("群聊", "群聊"),
    OFFICIALS("公众号", "公众号"),
    LABELS("标签", "标签好友"),
    ALL("全部", "全部")
}

internal enum class ContactPickerScopeTab(val label: String) {
    FRIENDS("好友"),
    LABELS("标签"),
    CONVERSATION_GROUPS("聊天分组")
}

internal data class ContactPickerScope(
    val name: String,
    val memberIds: Set<String>
)

internal enum class ConversationRuleCategory(val label: String) {
    OFFICIAL("公众号"),
    GROUP("群聊"),
    FRIEND("好友"),
    ALL("全部")
}

internal enum class MessageBlockContactKind {
    FRIEND,
    GROUP,
    OFFICIAL
}

internal enum class MessageBlockContactFilter(
    val label: String,
    val title: String
) {
    FRIENDS("好友", "好友"),
    GROUPS("群聊", "群聊"),
    OFFICIALS("公众号", "公众号"),
    LABELS("标签", "标签好友"),
    ALL("全部", "全部")
}

internal data class ContactPickerRequest(
    val title: String,
    val mode: ContactPickerMode,
    val multiSelect: Boolean,
    val existingValue: String,
    val onValue: (String) -> Unit,
    val enableLabels: Boolean = false,
    val enableGroupLabels: Boolean = true,
    val enableScopeSelection: Boolean = false,
    val singleConfirmText: String = "发送"
)

internal enum class AutoMessageForwardRoute {
    MAIN,
    RULES,
    EDITOR,
    CONTACTS,
    MEMBERS,
    TYPES,
    REPLACEMENTS
}

internal enum class AutoMessageForwardContactField {
    SOURCES,
    TARGETS
}

internal data class FavoritePickerRequest(
    val title: String,
    val existingValue: String,
    val onValue: (String) -> Unit,
    val multiSelect: Boolean = true,
    val delimiter: String = "|"
)

internal enum class FavoritePickerFilter(val label: String) {
    IMAGE("图片"),
    TEXT("文字"),
    VOICE("语音"),
    VIDEO("视频"),
    OTHER("其他"),
    ALL("全部");

    fun matches(type: Int): Boolean = when (this) {
        IMAGE -> type == 2
        TEXT -> type == 1
        VOICE -> type == 3
        VIDEO -> type == 4
        OTHER -> type !in setOf(1, 2, 3, 4)
        ALL -> true
    }
}

internal data class FavoriteMediaPreview(
    val item: WeChatFavoriteItem,
    val path: String
)

internal data class MessageBlockContactPickerRequest(
    val title: String,
    val existingValue: String,
    val onValue: (String) -> Unit,
    val allowOfficialAccounts: Boolean = true
)

internal data class GroupMemberPickerRequest(
    val title: String,
    val existingValue: String,
    val onValue: (String) -> Unit,
    val allowedGroupIds: Set<String>? = null
)

internal data class RedPacketTemplateEditorRequest(
    val index: Int,
    val template: RedPacketRuleTemplate,
    val canDelete: Boolean
)

internal data class RedPacketBindingEditorRequest(
    val index: Int,
    val binding: RedPacketRuleBinding,
    val canDelete: Boolean
)

internal data class TransferTemplateEditorRequest(
    val index: Int,
    val template: TransferRuleTemplate,
    val canDelete: Boolean
)

internal data class TransferBindingEditorRequest(
    val index: Int,
    val binding: TransferRuleBinding,
    val canDelete: Boolean
)

internal data class MessageBlockTemplateEditorRequest(
    val index: Int,
    val template: MessageBlockTemplate,
    val canDelete: Boolean
)

internal data class MessageBlockBindingEditorRequest(
    val index: Int,
    val binding: MessageBlockBinding,
    val canDelete: Boolean
)

internal data class MessageBlockBatchBindingEditorRequest(
    val title: String,
    val bindings: List<MessageBlockBinding>
)

internal data class GroupLeaveTemplateEditorRequest(
    val index: Int,
    val template: GroupLeaveReplyTemplate,
    val canDelete: Boolean
)

internal data class MessageBlockTypeOption(
    val key: String,
    val title: String,
    val summary: String
)

internal data class MessageBlockRuleState(
    val typeAll: Boolean,
    val types: Set<String>,
    val textKeywords: String
)

internal data class OptionPickerRequest(
    val title: String,
    val options: List<OptionItem>,
    val currentValue: Int,
    val onSelected: (OptionItem) -> Unit
)

internal data class OptionItem(
    val label: String,
    val value: Int,
    val summary: String = ""
)

internal data class PopupChoice<T>(
    val label: String,
    val value: T
)

internal fun optionItems(vararg items: Pair<String, Int>): List<OptionItem> {
    return items.map { OptionItem(label = it.first, value = it.second) }
}

internal fun ringtoneDisplayName(context: Context, uri: String, mode: Int): String {
    if (uri.isBlank()) return "跟随系统"
    val parsed = try {
        Uri.parse(uri)
    } catch (_: Throwable) {
        null
    }
    if (mode == RedPacketSettings.NOTIFY_SOUND_MODE_CUSTOM) {
        queryDisplayName(context, parsed)?.let { return it }
    }
    return try {
        val ringtone = parsed?.let { RingtoneManager.getRingtone(context, it) }
        ringtone?.getTitle(context)?.takeIf { it.isNotBlank() }
            ?: queryDisplayName(context, parsed)
            ?: "已选择自定义铃声"
    } catch (_: Throwable) {
        queryDisplayName(context, parsed)
            ?: "已选择自定义铃声"
    }
}

internal fun queryDisplayName(context: Context, uri: Uri?): String? {
    if (uri == null) return null
    if ("content".equals(uri.scheme, ignoreCase = true)) {
        try {
            context.contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
                ?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (index >= 0) {
                            return cursor.getString(index)?.takeIf { it.isNotBlank() }
                        }
                    }
                }
        } catch (_: Throwable) {
        }
    }
    return uri.lastPathSegment
        ?.let { Uri.decode(it) }
        ?.substringAfterLast('/')
        ?.substringAfterLast(':')
        ?.takeIf { it.isNotBlank() }
}

internal data class ContactOption(
    val id: String,
    val label: String,
    val group: Boolean,
    val avatarUrl: String,
    val avatarBackupUrl: String,
    val labels: List<String> = emptyList(),
    val official: Boolean = false,
    val extraSummary: String = "",
    val searchAliases: List<String> = emptyList()
)

internal fun ContactOption.matchesSearch(lower: String): Boolean {
    return lower.isEmpty() ||
        label.lowercase(Locale.US).contains(lower) ||
        id.lowercase(Locale.US).contains(lower) ||
        labels.any { it.lowercase(Locale.US).contains(lower) } ||
        extraSummary.lowercase(Locale.US).contains(lower) ||
        searchAliases.any { it.lowercase(Locale.US).contains(lower) }
}

internal data class MessageBlockContactOption(
    val contact: ContactOption,
    val kind: MessageBlockContactKind,
    val labelNames: List<String> = emptyList()
)

internal data class MessageBlockLabelOption(
    val id: String,
    val name: String,
    val contactIds: List<String>
)

internal data class MessageBlockContactData(
    val contacts: List<MessageBlockContactOption> = emptyList(),
    val labels: List<MessageBlockLabelOption> = emptyList()
)

internal data class MessageBlockInitialState(
    val templates: List<MessageBlockTemplate>,
    val bindings: List<MessageBlockBinding>,
    val shouldPersist: Boolean
)

internal fun newRedPacketTemplate(index: Int, sp: SharedPreferences): RedPacketRuleTemplate {
    val replyEnabled = sp.getBoolean(RedPacketSettings.KEY_REPLY_ENABLE, false)
    val storedReplyMode = if (replyEnabled) {
        sp.getInt(RedPacketSettings.KEY_REPLY_TYPE, RedPacketRuleConfig.REPLY_TEXT)
    } else {
        RedPacketRuleConfig.REPLY_OFF
    }
    val replySteps = loadGlobalRedPacketReplySteps(sp)
    val firstReply = replySteps.firstOrNull()
    val legacyReplyText = if (redPacketReplyUsesText(storedReplyMode)) {
        sp.getString(
            RedPacketSettings.KEY_REPLY_TEMPLATES,
            sp.getString(RedPacketSettings.KEY_REPLY_TEXT, "谢谢老板") ?: "谢谢老板"
        ) ?: "谢谢老板"
    } else {
        sp.getString(RedPacketSettings.KEY_REPLY_MEDIA_PATHS, "") ?: ""
    }
    return RedPacketRuleTemplate(
        id = System.currentTimeMillis().toString() + "_" + index,
        name = "模板 $index",
        enabled = false,
        grabMode = sp.getInt(RedPacketSettings.KEY_GRAB_MODE, RedPacketSettings.DEFAULT_GRAB_MODE),
        delayMode = redPacketDelayModeFromPrefs(sp),
        delayMs = redPacketDelayMillisFromPrefs(sp),
        randomMinMs = sp.getInt(RedPacketSettings.KEY_DELAY_RANDOM_MIN, 500).coerceAtLeast(0).toLong(),
        randomMaxMs = sp.getInt(RedPacketSettings.KEY_DELAY_RANDOM_MAX, 3000).coerceAtLeast(0).toLong(),
        skipSelf = sp.getBoolean(RedPacketSettings.KEY_SKIP_SELF, false),
        listMode = 0,
        whitelist = "",
        blacklist = "",
        keywordMode = sp.getInt(RedPacketSettings.KEY_KW_MODE, 0),
        keywords = sp.getString(RedPacketSettings.KEY_KEYWORDS, "") ?: "",
        quietEnabled = false,
        quietStartSecond = 0,
        quietEndSecond = 0,
        replyMode = firstReply?.mode ?: normalizeRedPacketReplyModeForUi(storedReplyMode),
        replyText = firstReply?.content ?: normalizeRedPacketReplyTextForUi(storedReplyMode, legacyReplyText),
        replyDelayMs = firstReply?.delayMs ?: redPacketReplyDelayMillisFromPrefs(sp),
        replyRandom = firstReply?.random ?: sp.getBoolean(RedPacketSettings.KEY_REPLY_RANDOM, false),
        replySteps = replySteps,
        groupReplySteps = loadGlobalGroupRedPacketReplySteps(sp),
        notificationConfigured = true,
        notifySystemEnabled = sp.getBoolean(RedPacketSettings.KEY_NOTIFY_SYSTEM_ENABLE, false),
        notifyToastEnabled = sp.getBoolean(RedPacketSettings.KEY_NOTIFY_TOAST_ENABLE, false),
        notifySoundEnabled = sp.getBoolean(RedPacketSettings.KEY_NOTIFY_SOUND_ENABLE, false),
        notifySoundMode = sp.getInt(RedPacketSettings.KEY_NOTIFY_SOUND_MODE, RedPacketSettings.NOTIFY_SOUND_MODE_SYSTEM),
        notifyVibrateEnabled = sp.getBoolean(RedPacketSettings.KEY_NOTIFY_VIBRATE_ENABLE, false),
        notifySoundUri = sp.getString(RedPacketSettings.KEY_NOTIFY_SOUND_URI, "") ?: "",
        notifyText = sp.getString(RedPacketSettings.KEY_NOTIFY_TEXT, "抢到红包 {amount} 元") ?: "抢到红包 {amount} 元",
        notifyToastText = sp.getString(
            RedPacketSettings.KEY_NOTIFY_TOAST_TEXT,
            sp.getString(RedPacketSettings.KEY_NOTIFY_TEXT, "抢到红包 {amount} 元") ?: "抢到红包 {amount} 元"
        ) ?: "抢到红包 {amount} 元",
        notifyFailedSystemEnabled = sp.getBoolean(RedPacketSettings.KEY_NOTIFY_FAILED_SYSTEM_ENABLE, false),
        notifyFailedToastEnabled = sp.getBoolean(RedPacketSettings.KEY_NOTIFY_FAILED_TOAST_ENABLE, false),
        notifyFailedText = sp.getString(RedPacketSettings.KEY_NOTIFY_FAILED_TEXT, "未抢到红包") ?: "未抢到红包",
        notifyFailedToastText = sp.getString(
            RedPacketSettings.KEY_NOTIFY_FAILED_TOAST_TEXT,
            sp.getString(RedPacketSettings.KEY_NOTIFY_FAILED_TEXT, "未抢到红包") ?: "未抢到红包"
        ) ?: "未抢到红包",
        announceEnabled = sp.getBoolean(RedPacketSettings.KEY_ANNOUNCE_ENABLE, false),
        announceText = sp.getString(RedPacketSettings.KEY_ANNOUNCE_TEXT, "抢到红包 {amount} 元") ?: "抢到红包 {amount} 元"
    )
}

