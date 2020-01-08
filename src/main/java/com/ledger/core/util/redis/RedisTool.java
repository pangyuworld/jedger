package com.ledger.core.util.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author pang
 * @version V1.0
 * @ClassName: RedisTool
 * @Package com.pang.mall.utils.redis
 * @description: redis&#x5de5;&#x5177;&#x7c7b;
 * @date 2019/11/12 8:38
 */
@Component
@Slf4j
public class RedisTool {
    @Autowired
    private RedisTemplate redisTemplate;
    private ValueOperations redisValue;
    private ListOperations redisList;
    private HashOperations redisHash;
    private SetOperations redisSet;
    private ZSetOperations redisZSet;

    @PostConstruct
    public void init() {
        log.debug("初始化各种template");
        redisValue = redisTemplate.opsForValue();
        redisList = redisTemplate.opsForList();
        redisHash = redisTemplate.opsForHash();
        redisSet = redisTemplate.opsForSet();
        redisZSet = redisTemplate.opsForZSet();
        log.debug("初始化完成各种template");
    }

    /**
     * 添加默认键值对
     *
     * @param key   键
     * @param value 值
     */
    public Boolean set(Object key, Object value) {
        try {
            redisValue.set(key, value, this.getExpire(key), TimeUnit.MILLISECONDS);
            log.debug("添加键值对成功,Object={},V={}", key, value);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.info("添加键值对失败,Object={},V={}", key, value, e);
            return Boolean.FALSE;
        }
    }

    /**
     * 添加默认键值对及设置过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间
     */
    public Boolean set(Object key, Object value, Long time) {
        try {
            redisValue.set(key, value, time, TimeUnit.MILLISECONDS);
            log.debug("添加键值对成功,Object={},V={},TimeOut={}", key, value, time);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.info("添加键值对失败,Object={},V={},TimeOut={}", key, value, time, e);
            return Boolean.FALSE;
        }
    }

    /**
     * 设置指定key的失效时间
     *
     * @param key  键
     * @param time 失效时间
     */
    public Boolean expire(Object key, Long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
                log.debug("设置指定key的失效时间成功,Object={},TimeOut={}", key, time);
                return Boolean.TRUE;
            }
            log.info("设置指定key的失效时间失败，原因是时间值小于0,Object={},TimeOut={}", key, time);
        } catch (Exception e) {
            log.info("设置指定key的失效时间失败,Object={},TimeOut={}", key, time, e);
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    /**
     * 根据key获得过期时间
     *
     * @param key 键
     * @return 时间（毫秒），0代表永久
     */
    public Long getExpire(Object key) {
        Long timeOut = null;
        try {
            timeOut = redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
            log.debug("根据key获取过期时间,Object={},TimeOut={}", key, timeOut);
        } catch (Exception e) {
            log.info("根据key获取过期时间失败,Object={},TimeOut={}", key, timeOut, e);
        }
        return timeOut;
    }

    /**
     * 判断key是否存在
     *
     * @param key 键值
     */
    public Boolean containsObjectey(Object key) {
        try {
            Boolean hasObjectey = redisTemplate.hasKey(key);
            log.debug("判断key是否存在,Object={},exist={}", key, hasObjectey);
            return hasObjectey;
        } catch (Exception e) {
            log.debug("判断key是否存在失败,Object={}", key, e);
            return Boolean.FALSE;
        }
    }

    /**
     * 删除key
     *
     * @param keys 键
     */
    public Boolean removeObjectey(Object... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete(Arrays.asList(keys));
            }
            log.debug("手动删除key成功,ObjectS={}", keys);
            return Boolean.TRUE;
        }
        log.info("手动删除key失败,ObjectS={}", keys);
        return Boolean.FALSE;
    }

    /**
     * 获得值
     *
     * @param key 键
     * @return 值
     */
    public Object get(Object key) {
        Object value = key == null ? null : redisValue.get(key);
        log.debug("获取key对应的值,Object={},V={}", key, value);
        return value;
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 递增因子
     * @return 递增后的大小
     */
    public Long increment(Object key, Long delta) {
        if (delta <= 0) {
            log.info("对值进行递增时出错，递增因子要大于0,Object={},D={}", key, delta);
            throw new RuntimeException("递增因子要大于0");
        }
        Long result = null;
        try {
            result = redisValue.increment(key, delta);
            log.debug("对值进行递增成功,Object={},D={},result={}", key, delta, result);
        } catch (Exception e) {
            log.info("对值进行递增失败,Object={},D={}", key, delta, e);
        }
        return result;
    }

    /**
     * 递增
     *
     * @param key 键
     * @return 递增后的大小
     */
    public Long increment(Object key) {
        return redisValue.increment(key);
    }

    /**
     * 递减
     *
     * @param key 键
     * @return 递减后的大小
     */
    public Long decrement(Object key) {
        return redisValue.decrement(key);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 递减因子
     * @return 递减后的大小
     */
    public Long decrement(Object key, Long delta) {
        if (delta <= 0) {
            log.info("对值进行递减时出错，递增因子要大于0,Object={},D={}", key, delta);
            throw new RuntimeException("递增因子要大于0");
        }
        Long result = null;
        try {
            result = redisValue.decrement(key, delta);
            log.debug("对值进行递减成功,Object={},D={},result={}", key, delta, result);
        } catch (Exception e) {
            log.info("对值进行递减失败,Object={},D={}", key, delta, e);
        }
        return result;
    }

    /**
     * 添加hash属性元素
     *
     * @param key   键
     * @param value 值
     */
    public Boolean setHash(Object key, Map<Object, Object> value) {
        try {
            redisHash.putAll(key, value);
            log.debug("将整个hash表添加到redis中,Object={},V={}", key, value);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.info("将整个hash表添加到redis中时候失败,Object={},V={}", key, value, e);
            return Boolean.FALSE;
        }
    }

    /**
     * 添加hash属性元素
     *
     * @param key   键
     * @param item  hash键
     * @param value hash值
     */
    public Boolean setHash(Object key, Object item, Object value) {
        try {
            redisHash.put(key, item, value);
            log.debug("向hash表中添加元素,Object={},HObject={},HV={}", key, item, value);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.info("向hash表中添加元素时候失败,Object={},HObject={},HV={}", key, item, value, e);
            return Boolean.FALSE;
        }
    }

    /**
     * 添加hash属性元素，并设置有效时间
     *
     * @param key   键
     * @param value 值
     * @param time  有效时间
     */
    public Boolean setHash(Object key, Map<Object, Object> value, Long time) {
        try {
            redisHash.putAll(key, value);
            expire(key, time);
            log.debug("添加整个hash表且设置过期时间,Object={},V={},TimeOut={}", key, value, time);
            return Boolean.TRUE;
        } catch (Exception e) { // 如果时间不大于0，则回滚
            this.removeObjectey(key);
            log.info("添加整个hash表且设置过期时间时候失败,Object={},V={},TimeOut={}", key, value, time, e);
            return Boolean.FALSE;
        }
    }

    /**
     * 添加hash属性元素，并设置有效时间
     *
     * @param key   键
     * @param item  hash键
     * @param value hash值
     * @param time  有效时间
     */
    public Boolean setHash(Object key, Object item, Object value, Long time) {
        try {
            redisHash.put(key, item, value);
            expire(key, time);
            log.debug("向hash表添加元素且更新过期时间,Object={},V={},TimeOut={}", key, value, time);
            return Boolean.TRUE;
        } catch (Exception e) {
            // 回滚
            this.removeHash(key, item);
            log.info("向hash表添加元素且更新过期时间失败,Object={},V={},TimeOut={}", key, value, time, e);
            return Boolean.FALSE;
        }
    }

    /**
     * 获取hash表中的某个值
     *
     * @param key  键
     * @param item hash键
     * @return hash值
     */
    public Object get(Object key, Object item) {
        try {
            Object value = redisHash.get(key, item);
            log.debug("从hash表中获取元素,Object={},HObject={},HV={}", key, item, value);
            return value;
        } catch (Exception e) {
            log.info("从hash表中获取元素失败,Object={},HObject={}", key, item, e);
            return null;
        }
    }

    /**
     * 获得整个hash表
     *
     * @param key 键
     * @return hash表
     */
    public Map<String, Object> getHash(Object key) {
        try {
            Map map = redisHash.entries(key);
            log.debug("获取整张hash表,Object={},V={}", key, map);
            return map;
        } catch (Exception e) {
            log.debug("获取整张hash表时失败,Object={}", key, e);
            return null;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key   键
     * @param items hash键
     */
    public Boolean removeHash(Object key, Object... items) {
        try {
            redisHash.delete(key, items);
            log.debug("删除hash表中的值成功,Object={},HObject={}", key, items);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.info("删除hash表中的值失败,Object={},HObject={}", key, items, e);
            return Boolean.FALSE;
        }
    }

    /**
     * 判断hash表中是否有hash键
     *
     * @param key  键
     * @param item hash键
     */
    public Boolean containsObjectey(Object key, Object item) {
        Boolean hasObjectey = redisHash.hasKey(key, item);
        log.debug("判断hash表中是否有hash键,Object={},HObject={}", key, item);
        return hasObjectey;
    }

    /**
     * 递增
     *
     * @param key   键
     * @param item  hash键
     * @param delta 递增因子
     * @return 递增后的值
     */
    public Long increment(Object key, Object item, Long delta) {
        try {
            Long result = redisHash.increment(key, item, delta);
            log.debug("对hash表中元素进行递增成功,Object={},HObject={},D={},result={}", key, item, delta, result);
            return result;
        } catch (Exception e) {
            log.debug("对hash表中元素进行递增失败,Object={},HObject={},D={}", key, item, delta, e);
        }
        return null;
    }

    /**
     * 递增
     *
     * @param key  键
     * @param item hash键
     * @return 递增后的值
     */
    public Long increment(Object key, Object item) {
        return increment(key, item, 1L);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param item  hash键
     * @param delta 递减因子
     * @return 递减后的值
     */
    public Long decrement(Object key, Object item, Long delta) {
        try {
            Long result = redisHash.increment(key, item, -delta);
            log.debug("对hash表中元素进行递键成功,Object={},HObject={},D={},result={}", key, item, -delta, result);
            return result;
        } catch (Exception e) {
            log.debug("对hash表中元素进行递键失败,Object={},HObject={},D={}", key, item, -delta, e);
        }
        return null;
    }

    /**
     * 递减
     *
     * @param key  键
     * @param item hash键
     * @return 递减后的值
     */
    public Long decrement(Object key, Object item) {
        return decrement(key, item, 1L);
    }

    /**
     * 推送消息
     *
     * @param channelName 通道名
     * @param message     消息
     */
    public void publish(String channelName, Object message) {
        try {
            redisTemplate.convertAndSend(channelName, message);
            log.debug("推送消息到消息队列,channel={},message={}", channelName, message);
        } catch (Exception e) {
            log.info("推送消息到消息队列失败,channel={},message={}", channelName, message, e);
        }
    }


}
